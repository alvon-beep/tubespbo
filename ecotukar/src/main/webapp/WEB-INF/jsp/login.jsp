<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Masuk - EcoTukar</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Plus Jakarta Sans', sans-serif;
        }
    </style>
</head>
<body class="min-h-screen bg-gradient-to-b from-emerald-50 via-white to-emerald-50/50 text-slate-800 antialiased flex items-center justify-center p-6">
    <div class="w-full max-w-md bg-white rounded-3xl shadow-2xl border border-emerald-100 overflow-hidden relative">
        <div class="absolute -top-10 -right-10 w-32 h-32 bg-gradient-to-br from-emerald-300 to-green-300 rounded-full blur-3xl opacity-50"></div>
        <div class="absolute -bottom-10 -left-10 w-32 h-32 bg-gradient-to-tr from-emerald-300 to-green-300 rounded-full blur-3xl opacity-50"></div>
        
        <div class="relative p-8 md:p-10">
            <div class="flex items-center justify-center gap-2.5 mb-8">
                <div class="w-10 h-10 rounded-xl bg-gradient-to-br from-emerald-500 to-green-600 grid place-items-center text-white font-extrabold shadow-md shadow-emerald-200">E</div>
                <span class="font-extrabold text-2xl tracking-tight bg-gradient-to-r from-emerald-700 to-green-700 bg-clip-text text-transparent">EcoTukar</span>
            </div>
            
            <h2 class="text-2xl font-bold text-center mb-2">Selamat Datang Kembali</h2>
            <p class="text-slate-500 text-sm text-center mb-8">Masuk ke akun untuk melanjutkan perjalanan menyelamatkan bumi bersama kami.</p>

            <% if (request.getParameter("error") != null) { %>
                <div class="bg-red-50 text-red-600 border border-red-200 text-sm font-semibold p-3 rounded-xl mb-6 text-center">
                    Username atau password salah.
                </div>
            <% } %>

            <% if (request.getParameter("logout") != null) { %>
                <div class="bg-emerald-50 text-emerald-700 border border-emerald-200 text-sm font-semibold p-3 rounded-xl mb-6 text-center">
                    Anda telah berhasil keluar.
                </div>
            <% } %>

            <% if (request.getParameter("registered") != null) { %>
                <div class="bg-teal-50 text-teal-700 border border-teal-200 text-sm font-semibold p-3 rounded-xl mb-6 text-center">
                    Pendaftaran berhasil! Silakan masuk dengan akun baru Anda.
                </div>
            <% } %>

            <form action="/login" method="post" class="space-y-5">
                <div>
                    <label for="username" class="block text-sm font-bold text-slate-700 mb-1.5">Username</label>
                    <input type="text" id="username" name="username" required placeholder="Masukkan username"
                        class="w-full px-4 py-3 rounded-xl border border-slate-200 bg-slate-50 focus:bg-white focus:ring-2 focus:ring-emerald-500/50 focus:border-emerald-500 outline-none transition font-medium">
                </div>
                <div>
                    <label for="password" class="block text-sm font-bold text-slate-700 mb-1.5">Password</label>
                    <input type="password" id="password" name="password" required placeholder="Masukkan password"
                        class="w-full px-4 py-3 rounded-xl border border-slate-200 bg-slate-50 focus:bg-white focus:ring-2 focus:ring-emerald-500/50 focus:border-emerald-500 outline-none transition font-medium">
                </div>
                
                <button type="submit" class="w-full py-3.5 mt-2 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold shadow-lg shadow-emerald-200 transition-all hover:-translate-y-0.5 active:translate-y-0">
                    Masuk Sekarang
                </button>
            </form>
            
            <div class="mt-6 text-center text-sm">
                <span class="text-slate-500">Belum punya akun?</span>
                <a href="/register" class="font-bold text-emerald-600 hover:text-emerald-700 hover:underline ml-1">Daftar Sekarang</a>
            </div>
            
            <div class="mt-4 text-center text-sm">
                <a href="/" class="font-bold text-emerald-600 hover:text-emerald-700 hover:underline">← Kembali ke Beranda</a>
            </div>

            <div class="mt-8 pt-6 border-t border-slate-100 text-xs text-slate-400 text-center">
                <p class="font-semibold text-slate-500 mb-2">Akun Uji Coba:</p>
                <div class="flex justify-center gap-4">
                    <span>Customer: <code class="font-bold">customer1</code></span>
                    <span>Kurir: <code class="font-bold">kurir1</code></span>
                    <span>Admin: <code class="font-bold">admin</code></span>
                </div>
                <p class="mt-1">Password untuk semua akun: <code class="font-bold">password</code></p>
            </div>
        </div>
    </div>
</body>
</html>
