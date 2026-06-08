<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Daftar - EcoTukar</title>
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
    <div class="w-full max-w-xl bg-white rounded-3xl shadow-2xl border border-emerald-100 overflow-hidden relative">
        <div class="absolute -top-10 -right-10 w-32 h-32 bg-gradient-to-br from-emerald-300 to-green-300 rounded-full blur-3xl opacity-50"></div>
        <div class="absolute -bottom-10 -left-10 w-32 h-32 bg-gradient-to-tr from-emerald-300 to-green-300 rounded-full blur-3xl opacity-50"></div>
        
        <div class="relative p-8 md:p-10">
            <div class="flex items-center justify-center gap-2.5 mb-8">
                <div class="w-10 h-10 rounded-xl bg-gradient-to-br from-emerald-500 to-green-600 grid place-items-center text-white font-extrabold shadow-md shadow-emerald-200">E</div>
                <span class="font-extrabold text-2xl tracking-tight bg-gradient-to-r from-emerald-700 to-green-700 bg-clip-text text-transparent">EcoTukar</span>
            </div>
            
            <h2 class="text-2xl font-bold text-center mb-2">Buat Akun Baru</h2>
            <p class="text-slate-500 text-sm text-center mb-8">Bergabunglah dan mulai perjalanan ramah lingkunganmu.</p>

            <% if (request.getParameter("error") != null) { %>
                <div class="bg-red-50 text-red-600 border border-red-200 text-sm font-semibold p-3 rounded-xl mb-6 text-center">
                    <%= request.getParameter("error") %>
                </div>
            <% } %>

            <form action="/register" method="post" class="space-y-4">
                <div class="grid md:grid-cols-2 gap-4">
                    <div>
                        <label for="name" class="block text-sm font-bold text-slate-700 mb-1.5">Nama Lengkap</label>
                        <input type="text" id="name" name="name" required placeholder="Budi Santoso"
                            class="w-full px-4 py-3 rounded-xl border border-slate-200 bg-slate-50 focus:bg-white focus:ring-2 focus:ring-emerald-500/50 focus:border-emerald-500 outline-none transition font-medium">
                    </div>
                    <div>
                        <label for="username" class="block text-sm font-bold text-slate-700 mb-1.5">Username</label>
                        <input type="text" id="username" name="username" required placeholder="budis"
                            class="w-full px-4 py-3 rounded-xl border border-slate-200 bg-slate-50 focus:bg-white focus:ring-2 focus:ring-emerald-500/50 focus:border-emerald-500 outline-none transition font-medium">
                    </div>
                </div>
                
                <div>
                    <label for="email" class="block text-sm font-bold text-slate-700 mb-1.5">Email</label>
                    <input type="email" id="email" name="email" required placeholder="budi@example.com"
                        class="w-full px-4 py-3 rounded-xl border border-slate-200 bg-slate-50 focus:bg-white focus:ring-2 focus:ring-emerald-500/50 focus:border-emerald-500 outline-none transition font-medium">
                </div>

                <div>
                    <label for="address" class="block text-sm font-bold text-slate-700 mb-1.5">Alamat Penjemputan</label>
                    <input type="text" id="address" name="address" required placeholder="Jl. Merdeka No. 123, Bandung"
                        class="w-full px-4 py-3 rounded-xl border border-slate-200 bg-slate-50 focus:bg-white focus:ring-2 focus:ring-emerald-500/50 focus:border-emerald-500 outline-none transition font-medium">
                </div>
                
                <div>
                    <label for="password" class="block text-sm font-bold text-slate-700 mb-1.5">Password</label>
                    <input type="password" id="password" name="password" required placeholder="Masukkan password"
                        class="w-full px-4 py-3 rounded-xl border border-slate-200 bg-slate-50 focus:bg-white focus:ring-2 focus:ring-emerald-500/50 focus:border-emerald-500 outline-none transition font-medium">
                </div>
                
                <button type="submit" class="w-full py-3.5 mt-4 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold shadow-lg shadow-emerald-200 transition-all hover:-translate-y-0.5 active:translate-y-0">
                    Daftar Sekarang
                </button>
            </form>
            
            <div class="mt-6 text-center text-sm">
                <span class="text-slate-500">Sudah punya akun?</span>
                <a href="/login" class="font-bold text-emerald-600 hover:text-emerald-700 hover:underline ml-1">Masuk di sini</a>
            </div>
        </div>
    </div>
</body>
</html>
