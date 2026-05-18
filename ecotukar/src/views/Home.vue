<!--
  Home.vue — EcoTukar Landing Page
  Target user: Public visitors
  Sections: Navbar, Hero, How It Works (3 steps), Impact stats, Footer
  Stack: Vue 3 (Composition API) + Tailwind CSS
-->
<script setup>
import { ref } from 'vue'

// Static mock data for "How it Works" steps
const steps = ref([
  {
    icon: '📱',
    title: 'Request Pickup',
    desc: 'Pesan penjemputan sampah dari rumah lewat aplikasi dalam hitungan detik.',
  },
  {
    icon: '🚛',
    title: 'Courier Collects',
    desc: 'Kurir EcoTukar datang ke alamatmu sesuai jadwal yang kamu pilih.',
  },
  {
    icon: '🎁',
    title: 'Earn Points',
    desc: 'Sampahmu ditimbang & dikonversi jadi koin yang bisa ditukar reward.',
  },
])

const stats = ref([
  { value: '12.4K', label: 'Pengguna Aktif' },
  { value: '320 Ton', label: 'Sampah Didaur Ulang' },
  { value: '85 Ton', label: 'CO₂ Dihemat' },
  { value: '4.9★', label: 'Rating Pengguna' },
])
</script>

<template>
  <div class="min-h-screen bg-gradient-to-b from-emerald-50 via-white to-emerald-50 font-sans text-slate-800">
    <!-- ===== Navbar ===== -->
    <header class="sticky top-0 z-50 backdrop-blur bg-white/70 border-b border-emerald-100">
      <nav class="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
        <div class="flex items-center gap-2">
          <div class="w-9 h-9 rounded-xl bg-gradient-to-br from-emerald-500 to-green-600 grid place-items-center text-white font-bold">E</div>
          <span class="font-extrabold text-lg tracking-tight">EcoTukar</span>
        </div>
        <ul class="hidden md:flex items-center gap-8 text-sm font-medium text-slate-600">
          <li><a href="#how" class="hover:text-emerald-600">Cara Kerja</a></li>
          <li><a href="#impact" class="hover:text-emerald-600">Dampak</a></li>
          <li><a href="#footer" class="hover:text-emerald-600">Kontak</a></li>
        </ul>
        <button class="px-5 py-2 rounded-full bg-emerald-600 text-white text-sm font-semibold shadow-md shadow-emerald-200 hover:bg-emerald-700 transition">
          Login / Masuk
        </button>
      </nav>
    </header>

    <!-- ===== Hero Section ===== -->
    <section class="max-w-7xl mx-auto px-6 pt-20 pb-24 grid md:grid-cols-2 gap-12 items-center">
      <div>
        <span class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-emerald-100 text-emerald-700 text-xs font-semibold">
          🌱 Solusi Sampah Rumah Tangga Digital
        </span>
        <h1 class="mt-5 text-4xl md:text-6xl font-extrabold leading-tight tracking-tight">
          Tukar Sampahmu, <span class="text-emerald-600">Selamatkan Bumi.</span>
        </h1>
        <p class="mt-6 text-lg text-slate-600 max-w-lg">
          EcoTukar menghubungkan rumah tangga dengan bank sampah digital. Pesan jemput, daur ulang, dan kumpulkan koin reward — semua dari satu aplikasi.
        </p>
        <div class="mt-8 flex flex-wrap gap-4">
          <button class="px-7 py-3 rounded-full bg-emerald-600 text-white font-semibold shadow-lg shadow-emerald-200 hover:bg-emerald-700 transition">
            Login / Masuk
          </button>
          <button class="px-7 py-3 rounded-full bg-white border border-emerald-200 text-emerald-700 font-semibold hover:bg-emerald-50 transition">
            Pelajari Lebih
          </button>
        </div>
      </div>

      <!-- Hero illustration card -->
      <div class="relative">
        <div class="absolute -inset-4 bg-gradient-to-tr from-emerald-200 to-green-100 rounded-3xl blur-2xl opacity-60"></div>
        <div class="relative bg-white rounded-3xl shadow-xl border border-emerald-100 p-8">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-xs text-slate-500">Total koin kamu</p>
              <p class="text-3xl font-extrabold text-emerald-600">1,240 🪙</p>
            </div>
            <div class="w-14 h-14 rounded-2xl bg-emerald-50 grid place-items-center text-2xl">♻️</div>
          </div>
          <div class="mt-6 space-y-3">
            <div v-for="(s, i) in [
              { t: 'Plastik PET', kg: '2.4 kg', c: '+120' },
              { t: 'Kertas Karton', kg: '5.1 kg', c: '+210' },
              { t: 'Botol Kaca', kg: '3.0 kg', c: '+90' },
            ]" :key="i" class="flex items-center justify-between p-3 rounded-xl bg-emerald-50/60">
              <div>
                <p class="text-sm font-semibold">{{ s.t }}</p>
                <p class="text-xs text-slate-500">{{ s.kg }}</p>
              </div>
              <span class="text-sm font-bold text-emerald-600">{{ s.c }} 🪙</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ===== How It Works ===== -->
    <section id="how" class="max-w-7xl mx-auto px-6 py-20">
      <div class="text-center mb-14">
        <h2 class="text-3xl md:text-4xl font-extrabold">Cara Kerja EcoTukar</h2>
        <p class="mt-3 text-slate-600">Tiga langkah mudah dari sampah jadi reward.</p>
      </div>
      <div class="grid md:grid-cols-3 gap-8">
        <div v-for="(step, i) in steps" :key="i" class="relative bg-white rounded-2xl p-8 border border-emerald-100 shadow-sm hover:shadow-xl hover:-translate-y-1 transition">
          <div class="absolute -top-4 -right-4 w-10 h-10 rounded-full bg-emerald-600 text-white font-bold grid place-items-center shadow-lg">{{ i + 1 }}</div>
          <div class="text-5xl mb-4">{{ step.icon }}</div>
          <h3 class="text-xl font-bold mb-2">{{ step.title }}</h3>
          <p class="text-slate-600 text-sm">{{ step.desc }}</p>
        </div>
      </div>
    </section>

    <!-- ===== Impact Stats ===== -->
    <section id="impact" class="max-w-7xl mx-auto px-6 py-16">
      <div class="rounded-3xl bg-gradient-to-r from-emerald-600 to-green-700 text-white p-10 md:p-14">
        <h2 class="text-3xl font-extrabold mb-8 text-center">Dampak Bersama Kita 🌍</h2>
        <div class="grid grid-cols-2 md:grid-cols-4 gap-6">
          <div v-for="(s, i) in stats" :key="i" class="text-center">
            <p class="text-3xl md:text-4xl font-extrabold">{{ s.value }}</p>
            <p class="text-emerald-100 text-sm mt-1">{{ s.label }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- ===== Footer ===== -->
    <footer id="footer" class="border-t border-emerald-100 bg-white mt-10">
      <div class="max-w-7xl mx-auto px-6 py-10 grid md:grid-cols-3 gap-8">
        <div>
          <div class="flex items-center gap-2">
            <div class="w-8 h-8 rounded-lg bg-emerald-600 grid place-items-center text-white font-bold">E</div>
            <span class="font-bold">EcoTukar</span>
          </div>
          <p class="mt-3 text-sm text-slate-600">Solusi digital pengelolaan sampah rumah tangga.</p>
        </div>
        <div>
          <h4 class="font-semibold mb-3">Tautan</h4>
          <ul class="space-y-2 text-sm text-slate-600">
            <li><a href="#" class="hover:text-emerald-600">Tentang</a></li>
            <li><a href="#" class="hover:text-emerald-600">Privasi</a></li>
            <li><a href="#" class="hover:text-emerald-600">Syarat & Ketentuan</a></li>
          </ul>
        </div>
        <div>
          <h4 class="font-semibold mb-3">Kontak</h4>
          <p class="text-sm text-slate-600">halo@ecotukar.id</p>
          <p class="text-sm text-slate-600">+62 812 3456 7890</p>
        </div>
      </div>
      <div class="border-t border-emerald-100 py-5 text-center text-xs text-slate-500">
        © 2026 EcoTukar. All rights reserved.
      </div>
    </footer>
  </div>
</template>