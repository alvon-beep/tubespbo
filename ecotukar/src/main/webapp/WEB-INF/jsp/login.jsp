<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="true" %>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>EcoTukar — Masuk / Daftar</title>
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
        .glass {
            background: rgba(255, 255, 255, 0.85);
            backdrop-filter: blur(12px);
            -webkit-backdrop-filter: blur(12px);
        }
    </style>
</head>
<body class="min-h-screen bg-gradient-to-br from-emerald-50 via-white to-emerald-100/50 flex flex-col items-center justify-center p-6 antialiased">

    <!-- Top Logo / Identity -->
    <div class="flex items-center gap-3 mb-8">
        <div class="w-11 h-11 rounded-2xl bg-gradient-to-br from-emerald-500 to-green-600 grid place-items-center text-white font-extrabold shadow-lg shadow-emerald-200 text-lg">E</div>
        <span class="font-extrabold text-2xl tracking-tight bg-gradient-to-r from-emerald-700 to-green-700 bg-clip-text text-transparent">EcoTukar</span>
    </div>

    <!-- Main Card Container -->
    <div class="w-full max-w-lg glass rounded-3xl border border-emerald-100/70 shadow-2xl p-8 space-y-6 relative overflow-hidden transition-all duration-300">
        
        <!-- Tab Switched Header -->
        <div class="flex items-center justify-between border-b border-emerald-50 pb-5">
            <div class="space-y-1">
                <h1 class="text-2xl font-extrabold text-slate-900 tracking-tight" id="card-title">Selamat Datang</h1>
                <p class="text-slate-400 text-xs font-semibold" id="card-subtitle">Silakan masuk ke akun EcoTukar Anda.</p>
            </div>
            
            <!-- Switch mode button -->
            <button onclick="toggleForm()" class="text-xs font-bold text-emerald-600 bg-emerald-50 hover:bg-emerald-100/80 px-3.5 py-2 rounded-xl transition" id="toggle-btn">
                Buat Akun Warga
            </button>
        </div>

        <!-- Role Select Grid -->
        <div class="space-y-2" id="role-selector-container">
            <label class="block text-[10px] font-bold text-slate-400 uppercase tracking-widest">Peran Masuk / Daftar</label>
            <div class="grid grid-cols-3 gap-2 bg-emerald-950/5 p-1 rounded-2xl border border-emerald-100/30">
                <button type="button" onclick="setRole('CUSTOMER')" id="role-customer-btn" class="py-2.5 rounded-xl text-xs font-extrabold transition shadow-sm bg-white text-emerald-700 border border-emerald-100/30">
                    👩‍🦰 Warga
                </button>
                <button type="button" onclick="setRole('TUKANG')" id="role-tukang-btn" class="py-2.5 rounded-xl text-xs font-extrabold transition text-slate-500 hover:text-slate-800">
                    👷 Kurir
                </button>
                <button type="button" onclick="setRole('ADMIN')" id="role-admin-btn" class="py-2.5 rounded-xl text-xs font-extrabold transition text-slate-500 hover:text-slate-800">
                    💼 Admin
                </button>
            </div>
        </div>

        <!-- Alert Notification Panel -->
        <div id="alert-panel" class="hidden p-4 rounded-2xl text-xs font-semibold border transition-all duration-200"></div>

        <!-- Form Elements -->
        <form id="authForm" class="space-y-4" onsubmit="handleSubmit(event)">
            
            <!-- Registration Fields (hidden by default) -->
            <div id="register-fields" class="hidden space-y-4 transition-all duration-300">
                <div>
                    <label class="block text-[10px] font-bold text-slate-400 uppercase tracking-widest mb-1.5">Nama Lengkap</label>
                    <input id="nama_lengkap" type="text" placeholder="Masukkan nama lengkap..." class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-200/50 focus:outline-none transition font-semibold text-slate-700 bg-slate-50/50 text-sm" />
                </div>
                <div>
                    <label class="block text-[10px] font-bold text-slate-400 uppercase tracking-widest mb-1.5">Nomor Telepon</label>
                    <input id="no_telepon" type="tel" placeholder="Contoh: 08123456789" class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-200/50 focus:outline-none transition font-semibold text-slate-700 bg-slate-50/50 text-sm" />
                </div>
            </div>

            <!-- Email & Password Fields -->
            <div>
                <label class="block text-[10px] font-bold text-slate-400 uppercase tracking-widest mb-1.5">Alamat Email</label>
                <input id="email" type="email" required placeholder="name@mail.com" class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-200/50 focus:outline-none transition font-semibold text-slate-700 bg-slate-50/50 text-sm" />
            </div>
            <div>
                <label class="block text-[10px] font-bold text-slate-400 uppercase tracking-widest mb-1.5">Kata Sandi</label>
                <input id="password" type="password" required placeholder="••••••••" class="w-full px-4 py-3 rounded-xl border border-slate-200 focus:border-emerald-400 focus:ring-2 focus:ring-emerald-200/50 focus:outline-none transition font-semibold text-slate-700 bg-slate-50/50 text-sm" />
            </div>

            <!-- Submit Buttons -->
            <div class="pt-2">
                <button type="submit" id="submit-btn" class="w-full py-3.5 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold shadow-lg shadow-emerald-200 hover:-translate-y-0.5 active:translate-y-0 transition duration-150 text-sm">
                    Masuk Sekarang
                </button>
            </div>
        </form>

        <!-- Back to Home Link -->
        <div class="text-center pt-2">
            <a href="/" class="text-xs font-semibold text-slate-400 hover:text-emerald-600 transition">← Kembali ke Beranda</a>
        </div>
    </div>

    <!-- JS Form & Tab Sync Logic -->
    <script>
        let isLogin = true;
        let selectedRole = 'CUSTOMER'; // CUSTOMER, TUKANG, ADMIN

        // Toggle Form mode (Login vs Register)
        function toggleForm() {
            isLogin = !isLogin;
            const regFields = document.getElementById('register-fields');
            const submitBtn = document.getElementById('submit-btn');
            const toggleBtn = document.getElementById('toggle-btn');
            const cardTitle = document.getElementById('card-title');
            const cardSubtitle = document.getElementById('card-subtitle');
            const alertPanel = document.getElementById('alert-panel');
            const roleContainer = document.getElementById('role-selector-container');

            // Reset alerts
            alertPanel.classList.add('hidden');

            if (isLogin) {
                // Set UI to LOGIN
                regFields.classList.add('hidden');
                document.getElementById('nama_lengkap').required = false;
                document.getElementById('no_telepon').required = false;
                submitBtn.innerText = "Masuk Sekarang";
                toggleBtn.innerText = "Buat Akun Warga";
                cardTitle.innerText = "Selamat Datang";
                cardSubtitle.innerText = "Silakan masuk ke akun EcoTukar Anda.";
                roleContainer.classList.remove('hidden');
            } else {
                // Set UI to REGISTER
                regFields.classList.remove('hidden');
                document.getElementById('nama_lengkap').required = true;
                document.getElementById('no_telepon').required = true;
                submitBtn.innerText = "Daftar Akun Baru";
                toggleBtn.innerText = "Sudah Punya Akun?";
                
                // Set register specific titles based on selectedRole
                if (selectedRole === 'CUSTOMER') {
                    cardTitle.innerText = "Daftar Akun Warga";
                    cardSubtitle.innerText = "Mulai menabung koin dengan mendaur ulang sampah.";
                } else if (selectedRole === 'TUKANG') {
                    cardTitle.innerText = "Daftar Akun Kurir";
                    cardSubtitle.innerText = "Daftarkan kurir baru untuk tim lapangan.";
                } else if (selectedRole === 'ADMIN') {
                    cardTitle.innerText = "Daftar Akun Admin";
                    cardSubtitle.innerText = "Registrasi administrator bank sampah.";
                }
            }
        }

        // Change Active Role Tab
        function setRole(role) {
            selectedRole = role;
            const roles = ['CUSTOMER', 'TUKANG', 'ADMIN'];
            
            roles.forEach(r => {
                const btn = document.getElementById(`role-${r.toLowerCase()}-btn`);
                if (r === role) {
                    btn.className = "py-2.5 rounded-xl text-xs font-extrabold transition shadow-sm bg-white text-emerald-700 border border-emerald-100/30";
                } else {
                    btn.className = "py-2.5 rounded-xl text-xs font-extrabold transition text-slate-500 hover:text-slate-800";
                }
            });

            // Update registration card headers dynamically if in registration mode
            if (!isLogin) {
                const cardTitle = document.getElementById('card-title');
                const cardSubtitle = document.getElementById('card-subtitle');
                if (role === 'CUSTOMER') {
                    cardTitle.innerText = "Daftar Akun Warga";
                    cardSubtitle.innerText = "Mulai menabung koin dengan mendaur ulang sampah.";
                } else if (role === 'TUKANG') {
                    cardTitle.innerText = "Daftar Akun Kurir";
                    cardSubtitle.innerText = "Daftarkan kurir baru untuk tim lapangan.";
                } else if (role === 'ADMIN') {
                    cardTitle.innerText = "Daftar Akun Admin";
                    cardSubtitle.innerText = "Registrasi administrator bank sampah.";
                }
            }
        }

        // Display alerts
        function showAlert(message, type = 'error') {
            const panel = document.getElementById('alert-panel');
            panel.classList.remove('hidden', 'bg-red-50', 'text-red-700', 'border-red-200', 'bg-emerald-50', 'text-emerald-700', 'border-emerald-200');
            
            panel.innerText = message;
            if (type === 'success') {
                panel.classList.add('bg-emerald-50', 'text-emerald-700', 'border-emerald-200');
            } else {
                panel.classList.add('bg-red-50', 'text-red-700', 'border-red-200');
            }
        }

        // Form Submission AJAX
        async function handleSubmit(e) {
            e.preventDefault();
            
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            const alertPanel = document.getElementById('alert-panel');
            
            alertPanel.classList.add('hidden');

            if (isLogin) {
                // HANDLE LOGIN FLOW
                try {
                    const res = await fetch('/api/login', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ email, password })
                    });
                    const data = await res.json();
                    
                    if (data.success) {
                        showAlert("Login berhasil! Mengalihkan ke dashboard...", "success");
                        setTimeout(() => {
                            // Direct mapping to their respective dashboards based on server response role
                            if (data.user.role === 'CUSTOMER') {
                                window.location.href = '/customer';
                            } else if (data.user.role === 'TUKANG') {
                                window.location.href = '/courier';
                            } else if (data.user.role === 'ADMIN') {
                                window.location.href = '/admin';
                            } else {
                                window.location.href = '/';
                            }
                        }, 1000);
                    } else {
                        showAlert(data.message || "Email atau password salah.");
                    }
                } catch (err) {
                    console.error(err);
                    showAlert("Terjadi kesalahan koneksi server.");
                }
            } else {
                // HANDLE REGISTER FLOW (Depends on Role Endpoint)
                const nama_lengkap = document.getElementById('nama_lengkap').value;
                const no_telepon = document.getElementById('no_telepon').value;
                
                let endpoint = '/api/register';
                if (selectedRole === 'ADMIN') {
                    endpoint = '/api/admin/register-admin';
                } else if (selectedRole === 'TUKANG') {
                    endpoint = '/api/admin/register-tukang';
                }

                try {
                    const res = await fetch(endpoint, {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ nama_lengkap, no_telepon, email, password })
                    });
                    const data = await res.json();
                    
                    if (data.success) {
                        showAlert(data.message || "Registrasi berhasil! Silakan masuk.", "success");
                        setTimeout(() => {
                            toggleForm(); // Switch back to login form
                        }, 1500);
                    } else {
                        showAlert(data.message || "Registrasi gagal.");
                    }
                } catch (err) {
                    console.error(err);
                    showAlert("Terjadi kesalahan koneksi server.");
                }
            }
        }
    </script>
</body>
</html>
