<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EcoTukar — Admin Control Panel</title>
    <!-- Tailwind CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Plus Jakarta Sans', sans-serif;
        }
    </style>
</head>
<body class="min-h-screen bg-slate-50 font-sans text-slate-800 flex flex-col lg:flex-row antialiased">

    <!-- ===== Floating Role Switcher for Verification ===== -->
    <div class="fixed bottom-6 right-6 z-50 bg-emerald-900/95 backdrop-blur-md text-emerald-100 p-4 rounded-2xl shadow-2xl border border-emerald-700/50 max-w-xs transition-all hover:scale-105">
        <p class="text-xs font-bold uppercase tracking-wider mb-2 text-emerald-400">⚡ Simulator Peran (Verify Flow)</p>
        <div class="grid grid-cols-2 gap-1.5 text-[11px] font-semibold">
            <a href="/" class="px-2.5 py-1.5 rounded-lg bg-emerald-950/50 hover:bg-emerald-800 hover:text-white transition text-center text-emerald-300">🏠 Home</a>
            <a href="/customer" class="px-2.5 py-1.5 rounded-lg bg-emerald-950/50 hover:bg-emerald-800 hover:text-white transition text-center text-emerald-300">👩‍🦰 Customer</a>
            <a href="/courier" class="px-2.5 py-1.5 rounded-lg bg-emerald-950/50 hover:bg-emerald-800 hover:text-white transition text-center text-emerald-300">👷 Kurir</a>
            <a href="/admin" class="px-2.5 py-1.5 rounded-lg bg-emerald-800 text-white border border-emerald-600/50 text-center">💼 Admin</a>
        </div>
    </div>

    <!-- ===== Sidebar ===== -->
    <aside class="hidden lg:flex w-64 bg-emerald-900 text-emerald-100 flex-col p-6 shrink-0 shadow-xl border-r border-emerald-950">
        <div class="flex items-center gap-2.5 mb-10">
            <div class="w-9 h-9 rounded-xl bg-emerald-500 grid place-items-center text-white font-extrabold shadow-md">E</div>
            <span class="font-extrabold text-lg tracking-tight text-white">EcoTukar Admin</span>
        </div>
        <nav class="space-y-2 text-sm font-semibold text-emerald-300">
            <a class="flex items-center gap-3 px-4 py-3 rounded-xl bg-emerald-800 text-white transition">📊 Dashboard</a>
            <a class="flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-emerald-800/40 hover:text-white transition">🗓️ Jadwal & Kurir</a>
            <a class="flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-emerald-800/40 hover:text-white transition">🪙 Konversi Koin</a>
            <a class="flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-emerald-800/40 hover:text-white transition">📈 Laporan</a>
            <a class="flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-emerald-800/40 hover:text-white transition">👥 Pengguna</a>
        </nav>
    </aside>

    <!-- ===== Main Dashboard ===== -->
    <main class="flex-1 p-6 lg:p-10 space-y-8 max-w-7xl mx-auto w-full">
        
        <!-- Header -->
        <header class="border-b border-slate-200 pb-5">
            <h1 class="text-2xl md:text-3xl font-extrabold text-slate-900 tracking-tight">Admin Operations Panel</h1>
            <p class="text-slate-500 text-sm font-medium mt-1">Kelola tiket penjemputan sampah, tugaskan kurir, dan konversi reward koin pelanggan.</p>
        </header>

        <!-- ===== Counters Grid ===== -->
        <section class="grid grid-cols-2 md:grid-cols-4 gap-5">
            <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm flex flex-col justify-between">
                <span class="text-2xl">♻️</span>
                <div class="mt-4">
                    <p class="text-2xl font-extrabold text-slate-950" id="count-weight">142 kg</p>
                    <p class="text-[10px] text-slate-400 font-bold uppercase tracking-wider mt-0.5">Berat Hari Ini</p>
                </div>
            </div>
            <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm flex flex-col justify-between">
                <span class="text-2xl">📦</span>
                <div class="mt-4">
                    <p class="text-2xl font-extrabold text-slate-950">3.4 Ton</p>
                    <p class="text-[10px] text-slate-400 font-bold uppercase tracking-wider mt-0.5">Berat Bulan Ini</p>
                </div>
            </div>
            <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm flex flex-col justify-between">
                <span class="text-2xl">🪙</span>
                <div class="mt-4">
                    <p class="text-2xl font-extrabold text-slate-950" id="count-coins">24,800</p>
                    <p class="text-[10px] text-slate-400 font-bold uppercase tracking-wider mt-0.5">Koin Terdistribusi</p>
                </div>
            </div>
            <div class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm flex flex-col justify-between">
                <span class="text-2xl">👥</span>
                <div class="mt-4">
                    <p class="text-2xl font-extrabold text-slate-950">1,204</p>
                    <p class="text-[10px] text-slate-400 font-bold uppercase tracking-wider mt-0.5">Pengguna Aktif</p>
                </div>
            </div>
        </section>

        <!-- ===== Schedule & Courier Table ===== -->
        <section class="bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
            <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between px-6 py-4.5 border-b border-slate-200 gap-3">
                <div>
                    <h3 class="font-extrabold text-slate-900 text-base">🗓️ Manajemen Jadwal & Kurir</h3>
                    <p class="text-xs text-slate-400 mt-0.5">Tugaskan kurir penjemputan sampah ke pesanan yang masuk.</p>
                </div>
                <button class="px-4 py-2 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white text-xs font-bold shadow-md shadow-emerald-100 hover:scale-[1.02] transition">
                    + Jadwal Manual
                </button>
            </div>
            <div class="overflow-x-auto">
                <table class="w-full text-sm text-left">
                    <thead class="bg-slate-50 text-slate-500 uppercase tracking-widest text-[9px] font-extrabold border-b border-slate-100">
                        <tr>
                            <th class="px-6 py-3.5">ID</th>
                            <th class="px-6 py-3.5">Pengguna</th>
                            <th class="px-6 py-3.5">Alamat</th>
                            <th class="px-6 py-3.5">Jenis</th>
                            <th class="px-6 py-3.5 text-center">Estimasi</th>
                            <th class="px-6 py-3.5 text-center">Tanggal</th>
                            <th class="px-6 py-3.5">Tugaskan Kurir</th>
                            <th class="px-6 py-3.5">Status</th>
                            <th class="px-6 py-3.5 text-center">Aksi</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-slate-100" id="schedule-rows">
                        <!-- Rows loaded dynamically -->
                    </tbody>
                </table>
            </div>
        </section>

        <!-- ===== Coin Conversion widget ===== -->
        <section class="grid lg:grid-cols-3 gap-8">
            
            <!-- Left inputs card -->
            <div class="lg:col-span-2 bg-white rounded-2xl p-6 border border-slate-200 shadow-sm space-y-5">
                <div>
                    <h3 class="font-extrabold text-slate-900 text-base">🪙 Sistem Konversi Koin</h3>
                    <p class="text-xs text-slate-400 mt-0.5">Validasi berat sampah riil dan transfer reward koin langsung ke saldo E-Wallet customer.</p>
                </div>
                <form id="conversionForm" class="grid md:grid-cols-2 gap-5">
                    <div>
                        <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-2">ID Permintaan</label>
                        <input id="convId" type="text" placeholder="REQ-1042" class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-100 focus:outline-none transition font-semibold text-slate-700 bg-slate-50" />
                    </div>
                    <div>
                        <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-2">Jenis Sampah</label>
                        <input id="convType" type="text" placeholder="Kertas" class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-100 focus:outline-none transition font-semibold text-slate-700 bg-slate-50" />
                    </div>
                    <div>
                        <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-2">Berat Riil (kg)</label>
                        <input id="convActualWeight" type="number" min="0" step="0.1" value="5" class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-100 focus:outline-none transition font-semibold text-slate-700 bg-slate-50" />
                    </div>
                    <div>
                        <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-2">Rate Reward (koin / kg)</label>
                        <input id="convRate" type="number" min="0" value="40" class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-100 focus:outline-none transition font-semibold text-slate-700 bg-slate-50" />
                    </div>
                    <div class="md:col-span-2 pt-2">
                        <button type="submit" class="w-full py-3.5 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold shadow-lg shadow-emerald-100 hover:-translate-y-0.5 active:translate-y-0 transition">
                            Simpan Konversi & Kirim Koin
                        </button>
                    </div>
                </form>
            </div>

            <!-- Right calculation showcase card -->
            <div class="bg-gradient-to-br from-emerald-600 to-green-700 rounded-2xl p-6 text-white shadow-xl flex flex-col justify-center border border-emerald-500/20 space-y-4">
                <div>
                    <p class="text-xs uppercase tracking-widest text-emerald-200 font-extrabold">Total Koin Dihasilkan</p>
                    <p class="text-5xl font-extrabold mt-1 tracking-tight" id="showcase-coins">200 🪙</p>
                </div>
                <p class="text-xs font-medium text-emerald-100/90 leading-relaxed border-t border-emerald-500/30 pt-3" id="showcase-desc">
                    Berdasarkan perhitungan berat riil <span class="font-extrabold" id="desc-weight">5.0</span> kg dari kategori <span class="font-extrabold" id="desc-type">Kertas</span> dengan tarif <span class="font-extrabold" id="desc-rate">40</span> koin/kg.
                </p>
            </div>
        </section>
    </main>

    <!-- ===== JS Logic for API Sync ===== -->
    <script>
        const scheduleRows = document.getElementById('schedule-rows');
        const countWeight = document.getElementById('count-weight');
        const countCoins = document.getElementById('count-coins');

        const convForm = document.getElementById('conversionForm');
        const convId = document.getElementById('convId');
        const convType = document.getElementById('convType');
        const convActualWeight = document.getElementById('convActualWeight');
        const convRate = document.getElementById('convRate');

        const showcaseCoins = document.getElementById('showcase-coins');
        const showcaseDesc = document.getElementById('showcase-desc');
        const descWeight = document.getElementById('desc-weight');
        const descType = document.getElementById('desc-type');
        const descRate = document.getElementById('desc-rate');

        const courierList = ['Budi S.', 'Eko P.', 'Wawan H.', 'Rizky A.'];

        // Live point calculations
        function calculateLiveCoins() {
            const weight = parseFloat(convActualWeight.value) || 0;
            const rate = parseInt(convRate.value) || 0;
            const type = convType.value || 'Umum';
            const coins = Math.round(weight * rate);

            showcaseCoins.innerText = `${coins.toLocaleString()} 🪙`;
            descWeight.innerText = weight.toFixed(1);
            descType.innerText = type;
            descRate.innerText = rate;
        }

        [convActualWeight, convRate, convType].forEach(el => {
            el.addEventListener('input', calculateLiveCoins);
        });

        // Load Tickets and compute Stats
        async function fetchAdminData() {
            try {
                const res = await fetch('/api/pickups');
                const pickups = await res.json();

                // Sum completed weights
                const completed = pickups.filter(p => p.status === 'COMPLETED');
                let weightToday = 0;
                completed.forEach(p => weightToday += p.actualWeight);

                countWeight.innerText = `${(142 + weightToday).toFixed(1)} kg`;

                // Sum points wallet histories dynamically
                const resTrans = await fetch('/api/transactions');
                const trans = await resTrans.json();
                let pointsGiven = 0;
                trans.forEach(t => {
                    if (t.delta.startsWith('+')) pointsGiven += parseInt(t.delta.replace('+', ''));
                });
                countCoins.innerText = (24800 + pointsGiven).toLocaleString();

                // Render table rows
                scheduleRows.innerHTML = '';
                pickups.forEach(p => {
                    const statusConfig = getStatusConfig(p.status);
                    
                    // Render Courier dropdown
                    let courierDropdown = '';
                    if (p.status !== 'COMPLETED' && p.status !== 'CANCELLED') {
                        courierDropdown = `
                            <select onchange="assignCourierToTicket('${p.id}', this.value)" class="px-2 py-1.5 rounded-lg border border-slate-200 text-xs font-semibold focus:outline-none bg-slate-50 text-slate-600 focus:border-emerald-300">
                                <option value="${p.courier}">${p.courier}</option>
                                ${courierList.filter(c => c !== p.courier).map(c => `<option value="${c}">${c}</option>`).join('')}
                            </select>
                        `;
                    } else {
                        courierDropdown = `<span class="text-xs font-bold text-slate-500 bg-slate-100 px-2.5 py-1 rounded-md border border-slate-200">${p.courier}</span>`;
                    }

                    // Weigh button for PICKED_UP status
                    let weighButton = '';
                    if (p.status === 'PICKED_UP') {
                        weighButton = `
                            <button onclick="autoPopulateWeighForm('${p.id}', '${p.wasteType}', ${p.estimatedWeight})" class="px-3 py-1.5 rounded-lg bg-emerald-100 hover:bg-emerald-200 text-emerald-800 text-[10px] font-extrabold hover:scale-105 active:scale-95 transition">
                                ⚖️ Timbang
                            </button>
                        `;
                    } else {
                        weighButton = `<span class="text-xs text-slate-400 font-bold">-</span>`;
                    }

                    scheduleRows.innerHTML += `
                        <tr class="hover:bg-emerald-50/20 transition">
                            <td class="px-6 py-4 font-mono text-xs text-slate-400">${p.id}</td>
                            <td class="px-6 py-4 font-bold text-slate-900">${p.customerName}</td>
                            <td class="px-6 py-4 text-xs text-slate-500 font-medium">${p.address}</td>
                            <td class="px-6 py-4 text-xs font-bold text-slate-600">${p.wasteType}</td>
                            <td class="px-6 py-4 text-center text-xs font-bold text-slate-600">${p.estimatedWeight} kg</td>
                            <td class="px-6 py-4 text-center text-xs font-semibold text-slate-500">${p.date}</td>
                            <td class="px-6 py-4">${courierDropdown}</td>
                            <td class="px-6 py-4">
                                <span class="text-[9px] font-extrabold px-2.5 py-1 rounded-full uppercase tracking-wider ${statusConfig.badge}">
                                    ${statusConfig.label}
                                </span>
                            </td>
                            <td class="px-6 py-4 text-center">${weighButton}</td>
                        </tr>
                    `;
                });

            } catch (e) {
                console.error("Error reading admin data", e);
            }
        }

        // Dropdown Courier Assignment POST
        async function assignCourierToTicket(id, courierName) {
            try {
                const res = await fetch(`/api/pickups/${id}/assign`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ courier: courierName })
                });
                
                if (res.ok) {
                    alert(`ID: ${id}\n\nTugas penjemputan berhasil diserahkan ke ${courierName}.`);
                    fetchAdminData();
                } else {
                    alert("Gagal merubah kurir.");
                }
            } catch(e) {
                console.error(e);
            }
        }

        // Timbang Auto-Populate Click handler
        function autoPopulateWeighForm(id, type, weight) {
            convId.value = id;
            convType.value = type;
            convActualWeight.value = weight;
            
            let rate = 40;
            if (type === 'Plastik') rate = 50;
            else if (type === 'Kertas') rate = 40;
            else if (type === 'Kaca') rate = 30;
            else if (type === 'Logam') rate = 100;
            else if (type === 'Elektronik') rate = 200;
            
            convRate.value = rate;

            calculateLiveCoins();

            document.getElementById('conversionForm').scrollIntoView({ behavior: 'smooth' });
        }

        // Submit Coin Conversion
        convForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const id = convId.value.trim();
            const actualWeight = parseFloat(convActualWeight.value);
            const ratePerKg = parseInt(convRate.value);

            if (!id) {
                alert("Mohon isikan ID Permintaan.");
                return;
            }

            try {
                const res = await fetch(`/api/pickups/${id}/convert`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ actualWeight, ratePerKg })
                });

                if (res.ok) {
                    alert(`Konversi Berhasil!\n\nSampah untuk ID ${id} telah divalidasi.\nKoin telah dikreditkan ke saldo Customer.`);
                    convForm.reset();
                    calculateLiveCoins();
                    fetchAdminData();
                } else {
                    alert("Gagal memproses konversi koin. Pastikan ID Permintaan valid.");
                }
            } catch(e) {
                console.error(e);
                alert("Kesalahan koneksi.");
            }
        });

        function getStatusConfig(status) {
            return {
                PENDING:    { label: 'Menunggu', badge: 'bg-amber-100 text-amber-700 border border-amber-200/50' },
                ASSIGNED:   { label: 'Ditugaskan', badge: 'bg-indigo-100 text-indigo-700 border border-indigo-200/50' },
                ON_ROUTE:   { label: 'Rute Jalan', badge: 'bg-sky-100 text-sky-700 border border-sky-200/50' },
                PICKED_UP:  { label: 'Diangkut', badge: 'bg-teal-100 text-teal-700 border border-teal-200/50' },
                COMPLETED:  { label: 'Selesai', badge: 'bg-emerald-100 text-emerald-700 border border-emerald-200/50' },
                CANCELLED:  { label: 'Batal', badge: 'bg-rose-100 text-rose-700 border border-rose-200/50' }
            }[status.toUpperCase()] || { label: status, badge: 'bg-slate-100 text-slate-700' };
        }

        // Initial load
        fetchAdminData();
        
        // Polling loop to sync states (every 4 seconds)
        setInterval(fetchAdminData, 4000);
    </script>
</body>
</html>
