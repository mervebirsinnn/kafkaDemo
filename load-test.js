import http from 'k6/http';
import { check, sleep } from 'k6';
import { SharedArray } from 'k6/data';

// 1. Harici kütüphane yerine yerleşik yöntemle CSV parse eden SharedArray
const csvData = new SharedArray('orders csv data', function () {
  // Dosyayı oku ve satırlara böl
  const f = open('./orders.csv');
  const lines = f.split('\n');
  const result = [];

  // İlk satır header (customerId,amount,currency) olduğu için 1'den başlıyoruz
  for (let i = 1; i < lines.length; i++) {
    const line = lines[i].trim();
    if (!line) continue; // Boş satırları atla

    const parts = line.split(',');
    if (parts.length >= 3) {
      result.push({
        customerName: parts[0].trim(),
        totalAmount: parts[1].trim(),
        currency:parts[2].trim(),

      });
    }
  }
  return result;
});

// 2. Yük Profili ve Senaryo Ayarları
export const options = {
  stages: [
    { duration: '30s', target: 1000 },  // 30 saniyede 10 kullanıcıya çık
    { duration: '1m', target: 30 },   // 1 dakika boyunca 30 kullanıcı ile yüklen
    { duration: '30s', target: 0 },   // 30 saniyede trafiği sıfırla
  ],
  thresholds: {
    http_req_duration: ['p(95)<500'], // İsteklerin %95'i 500ms altında olmalı
    http_req_failed: ['rate<0.01'],   // Hata oranı %1'in altında olmalı
  },
};

export default function () {
  // Havuzdan rastgele bir satır seç
  const randomRow = csvData[Math.floor(Math.random() * csvData.length)];
  const url = 'http://localhost:8080/orders';

const payload = JSON.stringify({
    customerName: randomRow.customerName,
    totalAmount: parseFloat(randomRow.totalAmount),
    currency: randomRow.currency,
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const res = http.post(url, payload, params);
if (res.status !== 201) {
    console.log(`STATUS: ${res.status} | BODY: ${res.body} | PAYLOAD: ${payload}`);
  }
  check(res, {
    'status is 201 Created': (r) => r.status === 201,
    'response has order id': (r) => r.json('id') !== undefined,
  });

  sleep(1);
}