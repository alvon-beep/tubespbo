<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="true" %>
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



    <!-- ===== Sidebar ===== -->
    <aside class="hidden lg:flex w-64 bg-emerald-900 text-emerald-100 flex-col p-6 shrink-0 shadow-xl border-r border-emerald-950">
        <div class="flex items-center gap-2.5 mb-10">
            <div class="w-9 h-9 rounded-xl bg-emerald-500 grid place-items-center text-white font-extrabold shadow-md">E</div>
            <span class="font-extrabold text-lg tracking-tight text-white">EcoTukar Admin</span>
        </div>
        <nav class="space-y-2 text-sm font-semibold text-emerald-300">
            <a onclick="switchTab('dashboard')" id="nav-dashboard" class="flex items-center gap-3 px-4 py-3 rounded-xl bg-emerald-800 text-white transition cursor-pointer">📊 Dashboard</a>
            <a onclick="switchTab('schedule')" id="nav-schedule" class="flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-emerald-800/40 hover:text-white transition cursor-pointer">🗓️ Jadwal & Kurir</a>
            <a class="flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-emerald-800/40 hover:text-white transition cursor-pointer">🪙 Konversi Koin</a>
            <a class="flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-emerald-800/40 hover:text-white transition cursor-pointer">📈 Laporan</a>
            <a class="flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-emerald-800/40 hover:text-white transition cursor-pointer">👥 Pengguna</a>
            <form action="/logout" method="post" class="mt-8 border-t border-emerald-800 pt-4">
                <button type="submit" class="w-full flex items-center gap-3 px-4 py-3 rounded-xl text-rose-400 hover:bg-rose-500/20 hover:text-rose-300 transition font-bold">🚪 Keluar</button>
            </form>
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


        <!-- ===== VIEW: DASHBOARD ===== -->
        <div id="view-dashboard" class="space-y-8 block animate-in fade-in duration-300">
            
            <!-- ===== Coin Conversion widget & Courier Registration ===== -->
            <section class="grid lg:grid-cols-3 gap-8">
                
                <!-- Coin Conversion Card -->
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

                <!-- Courier Registration Card -->
                <div class="bg-white rounded-2xl p-6 border border-slate-200 shadow-sm flex flex-col justify-between">
                    <div>
                        <h3 class="font-extrabold text-slate-900 text-base mb-1">👷 Daftarkan Kurir Baru</h3>
                        <p class="text-xs text-slate-400 mb-5">Tambahkan akun kurir ke sistem penjemputan.</p>
                        
                        <form id="form-tambah-kurir" class="space-y-4">
                            <div>
                                <label class="block text-[10px] font-bold text-slate-500 uppercase tracking-wider mb-1.5">Nama Lengkap Kurir</label>
                                <input id="newCourierName" type="text" required placeholder="Bambang Wijaya" class="w-full px-3 py-2.5 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-100 focus:outline-none transition text-sm bg-slate-50 font-semibold" />
                            </div>
                            <div>
                                <label class="block text-[10px] font-bold text-slate-500 uppercase tracking-wider mb-1.5">Username Login</label>
                                <input id="newCourierUsername" type="text" required placeholder="bambang" class="w-full px-3 py-2.5 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-100 focus:outline-none transition text-sm bg-slate-50 font-semibold" />
                            </div>
                            <div class="p-3 bg-amber-50 rounded-xl border border-amber-100 text-[10px] text-amber-700 font-medium">
                                ⚠️ Password <em>default</em> adalah <strong class="font-bold">password</strong>
                            </div>
                            <div class="pt-2">
                                <button type="submit" class="w-full py-2.5 rounded-xl bg-slate-800 hover:bg-slate-900 text-white font-bold transition shadow-md hover:-translate-y-0.5">
                                    Daftarkan Kurir
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

            </section>
        </div>

        <!-- ===== VIEW: SCHEDULE & COURIER ===== -->
        <div id="view-schedule" class="hidden animate-in fade-in duration-300">
            <!-- ===== Schedule & Courier Table ===== -->
            <section class="bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
                <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between px-6 py-4.5 border-b border-slate-200 gap-3">
                    <div>
                        <h3 class="font-extrabold text-slate-900 text-base">🗓️ Manajemen Jadwal & Kurir</h3>
                        <p class="text-xs text-slate-400 mt-0.5">Tugaskan kurir penjemputan sampah ke pesanan yang masuk.</p>
                    </div>
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
        </div>
    </main>

    <!-- ===== Modal Atur Jadwal ===== -->
    <div id="modal-tambah-manual" class="fixed inset-0 z-50 hidden bg-slate-900/50 backdrop-blur-sm flex items-center justify-center p-4">
        <div class="bg-white rounded-3xl w-full max-w-lg shadow-2xl overflow-hidden animate-in fade-in zoom-in duration-200">
            <div class="px-6 py-4 border-b border-slate-100 flex justify-between items-center bg-slate-50">
                <h3 class="font-bold text-slate-800 text-lg">Atur Jadwal Penjemputan</h3>
                <button onclick="document.getElementById('modal-tambah-manual').classList.add('hidden')" class="text-slate-400 hover:text-slate-600 transition">
                    ✖
                </button>
            </div>
            <div class="p-6">
                <form id="form-tambah-manual" class="space-y-4">
                    <input type="hidden" id="manual-id">
                    <div class="grid grid-cols-2 gap-4">
                        <div class="col-span-2">
                            <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-1.5">Nama Customer</label>
                            <input type="text" id="manual-name" readonly class="w-full px-3 py-2 rounded-lg border border-slate-200 bg-slate-100 text-slate-500 font-semibold outline-none text-sm cursor-not-allowed">
                        </div>
                    </div>
                    
                    <div>
                        <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-1.5">Alamat Penjemputan</label>
                        <input type="text" id="manual-address" readonly class="w-full px-3 py-2 rounded-lg border border-slate-200 bg-slate-100 text-slate-500 font-semibold outline-none text-sm cursor-not-allowed">
                    </div>

                    <div class="grid grid-cols-2 gap-4">
                        <div>
                            <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-1.5">Jenis Sampah</label>
                            <input type="text" id="manual-type" readonly class="w-full px-3 py-2 rounded-lg border border-slate-200 bg-slate-100 text-slate-500 font-semibold outline-none text-sm cursor-not-allowed">
                        </div>
                        <div>
                            <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-1.5">Estimasi Berat (Kg)</label>
                            <input type="text" id="manual-weight" readonly class="w-full px-3 py-2 rounded-lg border border-slate-200 bg-slate-100 text-slate-500 font-semibold outline-none text-sm cursor-not-allowed">
                        </div>
                    </div>

                    <div class="border-t border-slate-100 pt-4 mt-2">
                        <p class="text-xs text-emerald-600 font-bold mb-3 uppercase tracking-wider">Silakan Tentukan Jadwal:</p>
                        <div class="grid grid-cols-2 gap-4 mb-4">
                            <div>
                                <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-1.5">Tanggal</label>
                                <input type="date" id="manual-date" required class="w-full px-3 py-2 rounded-lg border border-slate-200 focus:border-emerald-500 focus:ring-1 focus:ring-emerald-500 outline-none text-sm">
                            </div>
                            <div>
                                <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-1.5">Waktu</label>
                                <input type="time" id="manual-time" required class="w-full px-3 py-2 rounded-lg border border-slate-200 focus:border-emerald-500 focus:ring-1 focus:ring-emerald-500 outline-none text-sm">
                            </div>
                        </div>

                        <div>
                            <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-1.5">Pilih Kurir</label>
                            <select id="manual-courier" required class="w-full px-3 py-2 rounded-lg border border-slate-200 focus:border-emerald-500 focus:ring-1 focus:ring-emerald-500 outline-none text-sm font-semibold text-emerald-700 bg-emerald-50">
                                <!-- Populated by JS -->
                            </select>
                        </div>
                    </div>
                    
                    <button type="submit" class="w-full py-3 mt-4 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold shadow-md transition-all active:scale-[0.98]">
                        Simpan & Tugaskan Kurir
                    </button>
                </form>
            </div>
        </div>
    </div>

    <!-- ===== JS Logic for API Sync ===== -->
    <script>
        const countCoins = document.getElementById('count-coins');
        const countWeight = document.getElementById('count-weight');

        // Tab Switching Logic
        function switchTab(tabId) {
            document.getElementById('view-dashboard').classList.add('hidden');
            document.getElementById('view-schedule').classList.add('hidden');
            
            document.getElementById('nav-dashboard').className = "flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-emerald-800/40 hover:text-white transition cursor-pointer";
            document.getElementById('nav-schedule').className = "flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-emerald-800/40 hover:text-white transition cursor-pointer";

            document.getElementById(`view-${tabId}`).classList.remove('hidden');
            document.getElementById(`nav-${tabId}`).className = "flex items-center gap-3 px-4 py-3 rounded-xl bg-emerald-800 text-white transition cursor-pointer";
        }

        // Initialize state
        let couriers = [];

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

        let courierList = []; // Will be populated dynamically

        // Handle Add Courier Form
        document.getElementById('form-tambah-kurir').addEventListener('submit', async (e) => {
            e.preventDefault();
            const btn = e.submitter;
            const originalText = btn.innerText;
            btn.innerText = 'Mendaftarkan...';
            btn.disabled = true;

            const name = document.getElementById('newCourierName').value;
            const username = document.getElementById('newCourierUsername').value;

            try {
                const res = await fetch('/api/admin/couriers', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ name, username })
                });
                const data = await res.json();
                
                if (data.status === 'success') {
                    alert(data.message);
                    document.getElementById('form-tambah-kurir').reset();
                    fetchAdminData(); // refresh UI
                } else {
                    alert(data.message || "Gagal menambahkan kurir.");
                }
            } catch (err) {
                console.error(err);
                alert('Terjadi kesalahan server saat mengatur jadwal.');
            } finally {
                btn.innerText = originalText;
                btn.disabled = false;
            }
        });

        // Live point calculations
        function calculateLiveCoins() {
            const weight = parseFloat(convActualWeight.value) || 0;
            const rate = parseInt(convRate.value) || 0;
            const type = convType.value || 'Umum';
            const coins = Math.round(weight * rate);

            if(showcaseCoins) showcaseCoins.innerText = `${coins.toLocaleString()} 🪙`;
            if(descWeight) descWeight.innerText = weight.toFixed(1);
            if(descType) descType.innerText = type;
            if(descRate) descRate.innerText = rate;
        }

        [convActualWeight, convRate, convType].forEach(el => {
            el.addEventListener('input', calculateLiveCoins);
        });

        // Load Tickets and compute Stats
        async function fetchAdminData() {
            try {
                // Fetch Couriers from DB
                const resCouriers = await fetch('/api/admin/couriers');
                if (resCouriers.ok) {
                    const couriers = await resCouriers.json();
                    courierList = couriers.map(c => c.name); // Store just names for dropdown
                    
                    // Populate manual courier select
                    let manualCourierSelect = document.getElementById('manual-courier');
                    if (manualCourierSelect) {
                        manualCourierSelect.innerHTML = `<option value="" disabled selected>-- Pilih Kurir --</option>`;
                        couriers.forEach(c => {
                            manualCourierSelect.innerHTML += `<option value="${c.name}">👷 ${c.name}</option>`;
                        });
                    }
                }

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
                const scheduleRows = document.getElementById('schedule-rows');
                if(scheduleRows) {
                    scheduleRows.innerHTML = '';
                    pickups.forEach(p => {
                        const statusConfig = getStatusConfig(p.status);
                        
                        // Render Courier dropdown OR Assign Schedule button
                        let courierDropdown = '';
                        if (p.status === 'PENDING') {
                            courierDropdown = `
                                <button onclick="openScheduleModal('${p.id}', '${p.customerName}', '${p.address}', '${p.wasteType}', ${p.estimatedWeight}, '${p.date}', '${p.time}')" class="px-3 py-1.5 rounded-lg bg-indigo-100 hover:bg-indigo-200 text-indigo-700 text-[10px] font-extrabold hover:scale-105 active:scale-95 transition">
                                    🗓️ Atur Jadwal
                                </button>
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
                }

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
            switchTab('dashboard');

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

            setTimeout(() => {
                document.getElementById('conversionForm').scrollIntoView({ behavior: 'smooth', block: 'center' });
            }, 100);
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

        // Open Schedule Modal
        function openScheduleModal(id, name, address, type, weight, date, time) {
            document.getElementById('manual-id').value = id;
            document.getElementById('manual-name').value = name;
            document.getElementById('manual-address').value = address;
            document.getElementById('manual-type').value = type;
            document.getElementById('manual-weight').value = weight;
            document.getElementById('manual-date').value = date;
            document.getElementById('manual-time').value = time;
            document.getElementById('manual-courier').value = '';
            document.getElementById('modal-tambah-manual').classList.remove('hidden');
        }

        // Handle tambah jadwal manual submit
        document.getElementById('form-tambah-manual').addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const id = document.getElementById('manual-id').value;
            const payload = {
                date: document.getElementById('manual-date').value,
                time: document.getElementById('manual-time').value,
                courier: document.getElementById('manual-courier').value
            };

            try {
                const res = await fetch(`/api/admin/pickups/${id}/schedule`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                
                const data = await res.json();
                if (data.status === 'success') {
                    alert(data.message);
                    document.getElementById('modal-tambah-manual').classList.add('hidden');
                    document.getElementById('form-tambah-manual').reset();
                    fetchAdminData();
                } else {
                    alert('Gagal: ' + data.message);
                }
            } catch (err) {
                console.error(err);
                alert('Terjadi kesalahan server saat mengatur jadwal.');
            }
        });

        // Initial load
        fetchAdminData();
        
        // Polling loop to sync states (every 4 seconds)
        setInterval(fetchAdminData, 4000);
    </script>
</body>
</html>
