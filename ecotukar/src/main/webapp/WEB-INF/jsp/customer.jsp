<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="true" %>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EcoTukar — Customer Dashboard</title>
    <!-- Tailwind CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
    <!-- Chart.js CDN -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
<body class="min-h-screen bg-emerald-50/30 font-sans text-slate-800 flex flex-col lg:flex-row antialiased">



    <!-- ===== Sidebar ===== -->
    <aside class="hidden lg:flex w-64 bg-white border-r border-emerald-100 flex-col p-6 shrink-0 shadow-sm">
        <div class="flex items-center gap-2.5 mb-10">
            <div class="w-9 h-9 rounded-xl bg-gradient-to-br from-emerald-500 to-green-600 grid place-items-center text-white font-extrabold shadow-md">E</div>
            <span class="font-extrabold text-lg tracking-tight bg-gradient-to-r from-emerald-700 to-green-700 bg-clip-text text-transparent">EcoTukar</span>
        </div>
        <nav class="space-y-2 text-sm font-semibold text-slate-500 font-semibold">
            <a onclick="switchTab('dashboard')" id="nav-dashboard" class="flex items-center gap-3 px-4 py-3 rounded-xl bg-emerald-50 text-emerald-700 transition cursor-pointer">🏠 Dashboard</a>
            <a onclick="switchTab('history')" id="nav-history" class="flex items-center gap-3 px-4 py-3 rounded-xl text-slate-500 hover:bg-emerald-50/50 hover:text-emerald-700 transition cursor-pointer">📦 Riwayat Pickup</a>
            <a class="flex items-center gap-3 px-4 py-3 rounded-xl text-slate-500 hover:bg-emerald-50/50 hover:text-emerald-700 transition cursor-pointer">🪙 E-Wallet</a>
            <a class="flex items-center gap-3 px-4 py-3 rounded-xl text-slate-500 hover:bg-emerald-50/50 hover:text-emerald-700 transition cursor-pointer">🎁 Reward</a>
            <a class="flex items-center gap-3 px-4 py-3 rounded-xl text-slate-500 hover:bg-emerald-50/50 hover:text-emerald-700 transition cursor-pointer">⚙️ Pengaturan</a>
            <form action="/logout" method="post" class="mt-8 border-t border-emerald-50 pt-4">
                <button type="submit" class="w-full flex items-center gap-3 px-4 py-3 rounded-xl text-rose-500 hover:bg-rose-50 hover:text-rose-700 transition font-bold">🚪 Keluar</button>
            </form>
        </nav>
    </aside>

    <!-- ===== Main Dashboard ===== -->
    <main class="flex-1 p-6 lg:p-10 space-y-8 max-w-7xl mx-auto w-full">
        
        <!-- Top Header -->
        <div class="flex items-center justify-between border-b border-emerald-50 pb-5">
            <div>
                <h1 class="text-2xl md:text-3xl font-extrabold text-slate-900 tracking-tight" id="greeting">Selamat datang, Sarah 👋</h1>
                <p class="text-slate-500 text-sm font-medium mt-1">Yuk, pesan penjemputan sampahmu untuk menabung koin.</p>
            </div>
            <div class="relative">
                <button class="w-10 h-10 rounded-full bg-white border border-emerald-100 text-slate-500 flex items-center justify-center font-bold hover:bg-emerald-50/50 transition">
                    🔔
                </button>
                <div class="absolute top-1 right-1 w-2.5 h-2.5 bg-rose-500 rounded-full border border-white"></div>
            </div>
        </div>

        <!-- ===== VIEW: DASHBOARD ===== -->
        <div id="view-dashboard" class="space-y-8 block animate-in fade-in duration-300">

        <!-- ===== Profile Section ===== -->
        <section class="bg-white rounded-2xl p-6 border border-emerald-100/70 shadow-sm flex flex-col md:flex-row items-center gap-6">
            <div class="w-16 h-16 rounded-2xl bg-emerald-100/60 grid place-items-center text-3xl shadow-inner" id="profile-avatar">👩‍🦰</div>
            <div class="flex-1 text-center md:text-left space-y-1">
                <h2 class="text-lg font-bold text-slate-900" id="profile-name">Sarah Putri</h2>
                <p class="text-sm text-slate-500 font-semibold" id="profile-email">sarah.putri@mail.com</p>
                <p class="text-xs text-slate-400 font-medium" id="profile-address">📍 Jl. Melati No. 21, Bandung</p>
            </div>
            <div class="text-center md:text-right border-t md:border-t-0 border-slate-100 pt-4 md:pt-0 w-full md:w-auto">
                <p class="text-[10px] uppercase tracking-wider text-slate-400 font-bold">Bergabung Sejak</p>
                <p class="text-sm font-extrabold text-slate-700 mt-1" id="profile-joined">Maret 2025</p>
            </div>
        </section>

        <!-- Two Columns widgets -->
        <div class="grid lg:grid-cols-3 gap-8">
            
            <!-- ===== Request Form Column ===== -->
            <section class="lg:col-span-2 bg-white rounded-2xl p-6 border border-emerald-100/70 shadow-sm space-y-6">
                <div>
                    <h3 class="text-lg font-extrabold text-slate-900">Request Pickup Sampah</h3>
                    <p class="text-slate-400 text-xs mt-0.5">Jadwalkan penjemputan sampah daur ulang Anda langsung dari depan rumah.</p>
                </div>
                <form id="pickupForm" class="grid md:grid-cols-2 gap-5">
                    <div>
                        <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-2">Jenis Sampah</label>
                        <select id="wasteType" class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-200/50 focus:outline-none transition font-semibold text-slate-700 bg-slate-50">
                            <option>Plastik</option>
                            <option>Kertas</option>
                            <option>Kaca</option>
                            <option>Logam</option>
                            <option>Organik</option>
                            <option>Elektronik</option>
                        </select>
                    </div>
                    <div>
                        <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-2">Estimasi Berat (kg)</label>
                        <input id="weight" type="number" min="1" value="3" class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-200/50 focus:outline-none transition font-semibold text-slate-700 bg-slate-50" />
                    </div>
                    <div>
                        <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-2">Tanggal Permintaan</label>
                        <input id="date" type="date" value="2026-06-03" class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-200/50 focus:outline-none transition font-semibold text-slate-700 bg-slate-50" />
                    </div>
                    <div>
                        <label class="block text-xs font-bold text-slate-500 uppercase tracking-wider mb-2">Catatan Kurir (opsional)</label>
                        <input id="note" type="text" placeholder="Letakkan di depan pagar..." class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-200/50 focus:outline-none transition font-semibold text-slate-700 bg-slate-50" />
                    </div>
                    <div class="md:col-span-2 pt-2">
                        <button type="submit" class="w-full py-3.5 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold shadow-lg shadow-emerald-200/80 hover:-translate-y-0.5 active:translate-y-0 transition duration-150">
                            Jadwalkan Pickup Sekarang
                        </button>
                    </div>
                </form>
            </section>

            <!-- ===== E-Wallet Card ===== -->
            <section class="bg-gradient-to-br from-emerald-600 to-green-700 rounded-2xl p-6 text-white shadow-xl flex flex-col justify-between border border-emerald-500/30">
                <div class="space-y-4">
                    <div>
                        <p class="text-xs uppercase tracking-widest text-emerald-200 font-extrabold">Saldo Koin Kamu</p>
                        <p class="text-5xl font-extrabold mt-1 tracking-tight" id="wallet-balance">0 🪙</p>
                    </div>
                    <button class="w-full py-2.5 rounded-xl bg-white/20 hover:bg-white/30 text-sm font-bold backdrop-blur-md transition">Tukar Reward</button>
                </div>
                <div class="mt-6 border-t border-emerald-500/40 pt-4 space-y-2.5">
                    <p class="text-[10px] uppercase font-bold tracking-widest text-emerald-200">Riwayat Terakhir</p>
                    <div class="space-y-2" id="wallet-history">
                        <!-- History loaded dynamically -->
                    </div>
                </div>
            </section>
        </div>

        <!-- ===== Impact Stats ===== -->
        <section class="space-y-4">
            <h3 class="text-lg font-extrabold text-slate-900 tracking-tight">🌍 Dampak Ekologis Kamu</h3>
            <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
                <div class="bg-white rounded-2xl p-5 border border-emerald-100/70 shadow-sm flex flex-col justify-between">
                    <span class="text-2xl">♻️</span>
                    <div class="mt-4">
                        <p class="text-xl font-extrabold text-emerald-800" id="impact-weight">0.0 kg</p>
                        <p class="text-[10px] text-slate-400 font-bold uppercase tracking-wider mt-0.5">Didaur Ulang</p>
                    </div>
                </div>
                <div class="bg-white rounded-2xl p-5 border border-emerald-100/70 shadow-sm flex flex-col justify-between">
                    <span class="text-2xl">🌱</span>
                    <div class="mt-4">
                        <p class="text-xl font-extrabold text-emerald-800" id="impact-co2">0.0 kg</p>
                        <p class="text-[10px] text-slate-400 font-bold uppercase tracking-wider mt-0.5">CO₂ Dihemat</p>
                    </div>
                </div>
                <div class="bg-white rounded-2xl p-5 border border-emerald-100/70 shadow-sm flex flex-col justify-between">
                    <span class="text-2xl">🌳</span>
                    <div class="mt-4">
                        <p class="text-xl font-extrabold text-emerald-800" id="impact-trees">0 pohon</p>
                        <p class="text-[10px] text-slate-400 font-bold uppercase tracking-wider mt-0.5">Pohon Setara</p>
                    </div>
                </div>
                <div class="bg-white rounded-2xl p-5 border border-emerald-100/70 shadow-sm flex flex-col justify-between">
                    <span class="text-2xl">🚛</span>
                    <div class="mt-4">
                        <p class="text-xl font-extrabold text-emerald-800" id="impact-count">0x</p>
                        <p class="text-[10px] text-slate-400 font-bold uppercase tracking-wider mt-0.5">Pickup Selesai</p>
                    </div>
                </div>
            </div>
        </section>

        <!-- ===== Chart Area ===== -->
        <section class="bg-white rounded-2xl p-6 border border-emerald-100/70 shadow-sm">
            <div class="flex items-center justify-between mb-6">
                <div>
                    <h4 class="font-extrabold text-slate-900">Statistik Kontribusi Sampah (kg)</h4>
                    <p class="text-xs text-slate-400 mt-0.5">Statistik kontribusi berat sampah Anda dalam 6 bulan terakhir.</p>
                </div>
                <span class="text-xs font-bold text-slate-400 bg-slate-100 px-3 py-1.5 rounded-lg border border-slate-200">Tahun 2026</span>
            </div>
            <div class="relative h-60 w-full">
                <canvas id="weightChart"></canvas>
            </div>
        </section>
        
        </div> <!-- End of VIEW: DASHBOARD -->

        <!-- ===== VIEW: HISTORY ===== -->
        <div id="view-history" class="hidden animate-in fade-in duration-300">
            <!-- ===== Pickup History ===== -->
            <section class="bg-white rounded-2xl border border-emerald-100/70 shadow-sm overflow-hidden">
                <div class="px-6 py-5 border-b border-slate-100">
                    <h3 class="text-lg font-extrabold text-slate-900 tracking-tight">📋 Riwayat Permintaan Pickup</h3>
                    <p class="text-xs text-slate-400 mt-1">Lacak status penjemputan sampahmu di sini.</p>
                </div>
                <div class="overflow-x-auto">
                    <table class="w-full text-sm text-left">
                        <thead class="bg-slate-50 text-slate-500 uppercase tracking-widest text-[10px] font-extrabold">
                            <tr>
                                <th class="px-6 py-3">ID</th>
                                <th class="px-6 py-3">Tanggal / Waktu</th>
                                <th class="px-6 py-3">Jenis Sampah</th>
                                <th class="px-6 py-3 text-center">Estimasi (kg)</th>
                                <th class="px-6 py-3">Kurir</th>
                                <th class="px-6 py-3">Status</th>
                            </tr>
                        </thead>
                        <tbody class="divide-y divide-slate-100" id="pickup-history-rows">
                            <!-- Loaded dynamically -->
                        </tbody>
                    </table>
                </div>
            </section>
        </div> <!-- End of VIEW: HISTORY -->

    </main>

    <!-- ===== JS Logic for API Sync ===== -->
    <script>
        // Tab Switching Logic
        function switchTab(tabId) {
            document.getElementById('view-dashboard').classList.add('hidden');
            document.getElementById('view-history').classList.add('hidden');
            
            document.getElementById('nav-dashboard').className = "flex items-center gap-3 px-4 py-3 rounded-xl text-slate-500 hover:bg-emerald-50/50 hover:text-emerald-700 transition cursor-pointer";
            document.getElementById('nav-history').className = "flex items-center gap-3 px-4 py-3 rounded-xl text-slate-500 hover:bg-emerald-50/50 hover:text-emerald-700 transition cursor-pointer";

            document.getElementById(`view-${tabId}`).classList.remove('hidden');
            document.getElementById(`nav-${tabId}`).className = "flex items-center gap-3 px-4 py-3 rounded-xl bg-emerald-50 text-emerald-700 transition cursor-pointer";
        }

        // Set modern input date defaults
        document.getElementById('date').valueAsDate = new Date();

        // References
        const walletBalance = document.getElementById('wallet-balance');
        const walletHistory = document.getElementById('wallet-history');
        const profileName = document.getElementById('profile-name');
        const profileEmail = document.getElementById('profile-email');
        const profileAddress = document.getElementById('profile-address');
        const profileJoined = document.getElementById('profile-joined');
        const profileAvatar = document.getElementById('profile-avatar');
        const greeting = document.getElementById('greeting');
        const pickupForm = document.getElementById('pickupForm');

        const impactWeight = document.getElementById('impact-weight');
        const impactCo2 = document.getElementById('impact-co2');
        const impactTrees = document.getElementById('impact-trees');
        const impactCount = document.getElementById('impact-count');

        let chart;
        const loggedInUser = '<%= request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "sarah" %>';

        // Fetch profile data
        async function fetchProfile() {
            try {
                const res = await fetch(`/api/profile?username=${loggedInUser}`);
                const user = await res.json();
                
                // Update views
                profileName.innerText = user.name;
                profileEmail.innerText = user.email;
                profileAddress.innerText = "📍 " + user.address;
                profileJoined.innerText = user.joined;
                profileAvatar.innerText = user.avatar;
                greeting.innerText = `Selamat datang, ${user.name.split(' ')[0]} 👋`;
                
                // Format points
                walletBalance.innerText = user.points.toLocaleString() + ' 🪙';

                // Recalculate Mock Ecological Stats based on points / pickups
                await fetchTransactionsAndRecalc(user.points);
            } catch(e) {
                console.error("Error fetching profile", e);
            }
        }

        async function fetchTransactionsAndRecalc(currentPoints) {
            try {
                // Fetch Wallet Point History
                const res = await fetch(`/api/transactions?username=${loggedInUser}`);
                const history = await res.json();

                // Populate history
                walletHistory.innerHTML = '';
                history.forEach(item => {
                    const isPlus = item.delta.startsWith('+');
                    const colorClass = isPlus ? 'text-emerald-200 bg-white/10' : 'text-rose-200 bg-white/10';
                    walletHistory.innerHTML += `
                        <div class="flex items-center justify-between text-xs rounded-xl px-3 py-2 bg-white/10">
                            <div>
                                <p class="font-bold">${item.title}</p>
                                <p class="text-[10px] opacity-75 mt-0.5">${item.date}</p>
                            </div>
                            <span class="font-extrabold px-2 py-1 rounded bg-white/10 ${colorClass}">${item.delta}</span>
                        </div>
                    `;
                });

                // Fetch pickups list to compute true impact metrics!
                const resPickups = await fetch('/api/pickups');
                const pickups = await resPickups.json();
                
                // Filter pickups for this user
                const userPickups = pickups.filter(p => p.username === loggedInUser);
                
                // Populate Pickup History Table
                const pickupHistoryRows = document.getElementById('pickup-history-rows');
                pickupHistoryRows.innerHTML = '';
                
                if (userPickups.length === 0) {
                    pickupHistoryRows.innerHTML = `<tr><td colspan="6" class="px-6 py-8 text-center text-slate-400 font-medium text-xs">Belum ada riwayat penjemputan.</td></tr>`;
                } else {
                    userPickups.reverse().forEach(p => {
                        let statusColor = "bg-slate-100 text-slate-500 border-slate-200";
                        let statusText = "Menunggu Admin";
                        
                        if (p.status === 'ASSIGNED') {
                            statusColor = "bg-amber-100 text-amber-700 border-amber-200";
                            statusText = "Telah Dijadwalkan";
                        } else if (p.status === 'ON_ROUTE') {
                            statusColor = "bg-blue-100 text-blue-700 border-blue-200";
                            statusText = "Kurir Menuju Lokasi";
                        } else if (p.status === 'PICKED_UP') {
                            statusColor = "bg-indigo-100 text-indigo-700 border-indigo-200";
                            statusText = "Sampah Diangkut";
                        } else if (p.status === 'COMPLETED') {
                            statusColor = "bg-emerald-100 text-emerald-700 border-emerald-200";
                            statusText = "Selesai (Koin Diterima)";
                        } else if (p.status === 'CANCELLED') {
                            statusColor = "bg-rose-100 text-rose-700 border-rose-200";
                            statusText = "Dibatalkan";
                        }

                        let dateLabel = "📅 Permintaan";
                        let dateColor = "text-slate-500";
                        if (p.status !== 'PENDING') {
                            dateLabel = "🚛 Jadwal Jemput";
                            dateColor = "text-indigo-600";
                        }

                        pickupHistoryRows.innerHTML += `
                            <tr class="hover:bg-emerald-50/30 transition">
                                <td class="px-6 py-4 font-mono text-[10px] text-slate-400 font-bold">${p.id}</td>
                                <td class="px-6 py-4">
                                    <div class="text-[9px] uppercase tracking-wider font-extrabold ${dateColor} mb-0.5">${dateLabel}</div>
                                    <div class="text-xs font-bold text-slate-700">${p.date}</div>
                                    <div class="text-[10px] text-slate-500 font-medium mt-0.5">${p.time || '-'}</div>
                                </td>
                                <td class="px-6 py-4 text-xs font-bold text-slate-600">${p.wasteType}</td>
                                <td class="px-6 py-4 text-center text-xs font-bold text-slate-600">${p.estimatedWeight} kg</td>
                                <td class="px-6 py-4 text-xs font-bold ${p.courier ? 'text-emerald-600' : 'text-slate-400'}">${p.courier || 'Belum Ditentukan'}</td>
                                <td class="px-6 py-4">
                                    <span class="text-[9px] font-extrabold px-2.5 py-1 rounded-md border uppercase tracking-wider ${statusColor}">
                                        ${statusText}
                                    </span>
                                </td>
                            </tr>
                        `;
                    });
                }

                const completedPickups = userPickups.filter(p => p.status === 'COMPLETED');
                const pickupTimes = completedPickups.length;

                // Total actual weight
                let totalWeight = 0;
                completedPickups.forEach(p => totalWeight += p.actualWeight);

                // Add default baseline weights so the card starts with data
                const baselineWeight = 48.2 + totalWeight; 
                const co2Saved = (baselineWeight * 0.257).toFixed(1);
                const treesSaved = Math.ceil(baselineWeight * 0.124);
                const totalPickups = 14 + pickupTimes;

                impactWeight.innerText = `${baselineWeight.toFixed(1)} kg`;
                impactCo2.innerText = `${co2Saved} kg`;
                impactTrees.innerText = `${treesSaved} pohon`;
                impactCount.innerText = `${totalPickups}x`;

                // Update dynamic Chart.js data
                updateChart(baselineWeight);

            } catch (e) {
                console.error(e);
            }
        }

        // Initialize and Update dynamic contribution charts using Chart.js
        function updateChart(latestWeight) {
            const baseValues = [4, 7, 5, 9, 11, 12];
            const addedWeight = latestWeight - 48.2;
            baseValues[5] = 12 + addedWeight;

            const ctx = document.getElementById('weightChart').getContext('2d');
            
            if (chart) {
                chart.data.datasets[0].data = baseValues;
                chart.update();
                return;
            }

            chart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Des', 'Jan', 'Feb', 'Mar', 'Apr', 'Mei'],
                    datasets: [{
                        label: 'Total Sampah (kg)',
                        data: baseValues,
                        backgroundColor: 'rgba(16, 185, 129, 0.75)',
                        borderColor: 'rgb(16, 185, 129)',
                        borderWidth: 2,
                        borderRadius: 8,
                        hoverBackgroundColor: 'rgba(16, 185, 129, 0.95)'
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: { display: false }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            grid: { color: 'rgba(241, 245, 249, 0.8)' }
                        },
                        x: {
                            grid: { display: false }
                        }
                    }
                }
            });
        }

        // Form Submit Handler
        pickupForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const wasteType = document.getElementById('wasteType').value;
            const weight = parseFloat(document.getElementById('weight').value);
            const date = document.getElementById('date').value;
            const note = document.getElementById('note').value;

            try {
                const res = await fetch('/api/pickups', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        wasteType, weight, date, note, username: loggedInUser
                    })
                });

                if (res.ok) {
                    const request = await res.json();
                    alert(`Permintaan jemput berhasil dibuat!\n\nID: ${request.id}\nJenis: ${request.wasteType}\nEstimasi: ${request.estimatedWeight} kg\nStatus: PENDING`);
                    pickupForm.reset();
                    document.getElementById('date').valueAsDate = new Date();
                    // Refetch data
                    fetchProfile();
                } else {
                    alert("Gagal menjadwalkan pickup.");
                }
            } catch(err) {
                console.error(err);
                alert("Kesalahan koneksi.");
            }
        });

        // Initial load
        fetchProfile();
        
        // Polling loop for active state sync across simulator pages (every 4 seconds)
        setInterval(fetchProfile, 4000);
    </script>
</body>
</html>
