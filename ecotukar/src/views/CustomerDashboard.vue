<!--
  CustomerDashboard.vue — EcoTukar Customer Dashboard
  Target user: Rumah tangga / household
  Sections: Sidebar, Profile, Pickup Request Form, Impact Tracker, E-Wallet Poin
  Stack: Vue 3 (Composition API) + Tailwind CSS
-->
<script setup>
import { ref, reactive } from 'vue'

// Mock profile data
const profile = ref({
  name: 'Sarah Putri',
  email: 'sarah.putri@mail.com',
  address: 'Jl. Melati No. 21, Bandung',
  joined: 'Maret 2025',
  avatar: '👩‍🦰',
})

// Reactive form for pickup request
const form = reactive({
  wasteType: 'Plastik',
  weight: 3,
  date: '2026-05-20',
  note: '',
})
const wasteTypes = ['Plastik', 'Kertas', 'Kaca', 'Logam', 'Organik', 'Elektronik']

// Mock impact stats
const impact = ref([
  { label: 'Sampah Didaur Ulang', value: '48.2 kg', icon: '♻️', color: 'emerald' },
  { label: 'CO₂ Dihemat', value: '12.4 kg', icon: '🌱', color: 'green' },
  { label: 'Pohon Setara', value: '6 pohon', icon: '🌳', color: 'teal' },
  { label: 'Pickup Selesai', value: '14x', icon: '🚛', color: 'lime' },
])

// Mock wallet history
const wallet = ref({
  balance: 1240,
  history: [
    { t: 'Pickup Plastik 2.4kg', date: '12 Mei 2026', delta: '+120' },
    { t: 'Tukar Voucher Belanja', date: '08 Mei 2026', delta: '-300' },
    { t: 'Pickup Kertas 5.1kg', date: '03 Mei 2026', delta: '+210' },
    { t: 'Bonus Referral', date: '01 Mei 2026', delta: '+50' },
  ],
})

function submitPickup() {
  alert(`Permintaan jemput terkirim!\nJenis: ${form.wasteType}\nBerat: ${form.weight} kg\nTanggal: ${form.date}`)
}
</script>

<template>
  <div class="min-h-screen bg-emerald-50/50 font-sans text-slate-800 flex">
    <!-- ===== Sidebar ===== -->
    <aside class="hidden lg:flex w-64 bg-white border-r border-emerald-100 flex-col p-6">
      <div class="flex items-center gap-2 mb-10">
        <div class="w-9 h-9 rounded-xl bg-gradient-to-br from-emerald-500 to-green-600 grid place-items-center text-white font-bold">E</div>
        <span class="font-extrabold text-lg">EcoTukar</span>
      </div>
      <nav class="space-y-2 text-sm font-medium">
        <a class="flex items-center gap-3 px-3 py-2 rounded-lg bg-emerald-100 text-emerald-700">🏠 Dashboard</a>
        <a class="flex items-center gap-3 px-3 py-2 rounded-lg text-slate-600 hover:bg-emerald-50">📦 Riwayat Pickup</a>
        <a class="flex items-center gap-3 px-3 py-2 rounded-lg text-slate-600 hover:bg-emerald-50">🪙 E-Wallet</a>
        <a class="flex items-center gap-3 px-3 py-2 rounded-lg text-slate-600 hover:bg-emerald-50">🎁 Reward</a>
        <a class="flex items-center gap-3 px-3 py-2 rounded-lg text-slate-600 hover:bg-emerald-50">⚙️ Pengaturan</a>
      </nav>
    </aside>

    <!-- ===== Main ===== -->
    <main class="flex-1 p-6 lg:p-10 space-y-8">
      <!-- Topbar -->
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-2xl md:text-3xl font-extrabold">Selamat datang, {{ profile.name.split(' ')[0] }} 👋</h1>
          <p class="text-slate-500 text-sm">Yuk, jadwalkan pickup sampahmu hari ini.</p>
        </div>
        <button class="px-4 py-2 rounded-full bg-white border border-emerald-200 text-emerald-700 text-sm font-semibold">🔔</button>
      </div>

      <!-- ===== Profile Section ===== -->
      <section class="bg-white rounded-2xl p-6 border border-emerald-100 shadow-sm flex items-center gap-5">
        <div class="w-16 h-16 rounded-full bg-emerald-100 grid place-items-center text-3xl">{{ profile.avatar }}</div>
        <div class="flex-1">
          <h2 class="text-lg font-bold">{{ profile.name }}</h2>
          <p class="text-sm text-slate-500">{{ profile.email }}</p>
          <p class="text-sm text-slate-500">📍 {{ profile.address }}</p>
        </div>
        <div class="hidden md:block text-right">
          <p class="text-xs text-slate-500">Bergabung</p>
          <p class="text-sm font-semibold">{{ profile.joined }}</p>
        </div>
      </section>

      <div class="grid lg:grid-cols-3 gap-6">
        <!-- ===== Pickup Request Form ===== -->
        <section class="lg:col-span-2 bg-white rounded-2xl p-6 border border-emerald-100 shadow-sm">
          <h3 class="text-lg font-bold mb-4">🚛 Request Pickup Sampah</h3>
          <form @submit.prevent="submitPickup" class="grid md:grid-cols-2 gap-5">
            <div>
              <label class="block text-xs font-semibold text-slate-600 mb-1">Jenis Sampah</label>
              <select v-model="form.wasteType" class="w-full px-4 py-2.5 rounded-xl border border-emerald-200 focus:ring-2 focus:ring-emerald-400 focus:outline-none">
                <option v-for="w in wasteTypes" :key="w">{{ w }}</option>
              </select>
            </div>
            <div>
              <label class="block text-xs font-semibold text-slate-600 mb-1">Estimasi Berat (kg)</label>
              <input v-model.number="form.weight" type="number" min="1" class="w-full px-4 py-2.5 rounded-xl border border-emerald-200 focus:ring-2 focus:ring-emerald-400 focus:outline-none" />
            </div>
            <div>
              <label class="block text-xs font-semibold text-slate-600 mb-1">Tanggal Penjemputan</label>
              <input v-model="form.date" type="date" class="w-full px-4 py-2.5 rounded-xl border border-emerald-200 focus:ring-2 focus:ring-emerald-400 focus:outline-none" />
            </div>
            <div>
              <label class="block text-xs font-semibold text-slate-600 mb-1">Catatan (opsional)</label>
              <input v-model="form.note" placeholder="Letakkan di depan pagar..." class="w-full px-4 py-2.5 rounded-xl border border-emerald-200 focus:ring-2 focus:ring-emerald-400 focus:outline-none" />
            </div>
            <div class="md:col-span-2">
              <button type="submit" class="w-full py-3 rounded-xl bg-emerald-600 text-white font-semibold shadow-md shadow-emerald-200 hover:bg-emerald-700 transition">
                Jadwalkan Pickup
              </button>
            </div>
          </form>
        </section>

        <!-- ===== E-Wallet Poin ===== -->
        <section class="bg-gradient-to-br from-emerald-600 to-green-700 rounded-2xl p-6 text-white shadow-lg">
          <p class="text-xs uppercase tracking-wider opacity-80">Saldo Koin</p>
          <p class="text-4xl font-extrabold mt-1">{{ wallet.balance.toLocaleString() }} 🪙</p>
          <button class="mt-4 w-full py-2 rounded-xl bg-white/20 hover:bg-white/30 text-sm font-semibold backdrop-blur">Tukar Reward</button>
          <div class="mt-5 space-y-2">
            <div v-for="(h, i) in wallet.history" :key="i" class="flex items-center justify-between text-sm bg-white/10 rounded-lg px-3 py-2">
              <div>
                <p class="font-semibold">{{ h.t }}</p>
                <p class="text-xs opacity-75">{{ h.date }}</p>
              </div>
              <span class="font-bold" :class="h.delta.startsWith('+') ? 'text-emerald-200' : 'text-rose-200'">{{ h.delta }}</span>
            </div>
          </div>
        </section>
      </div>

      <!-- ===== Impact Tracker ===== -->
      <section>
        <h3 class="text-lg font-bold mb-4">🌍 Dampak Lingkungan Kamu</h3>
        <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
          <div v-for="(it, i) in impact" :key="i" class="bg-white rounded-2xl p-5 border border-emerald-100 shadow-sm hover:shadow-md transition">
            <div class="text-3xl">{{ it.icon }}</div>
            <p class="mt-3 text-2xl font-extrabold text-emerald-700">{{ it.value }}</p>
            <p class="text-xs text-slate-500 mt-1">{{ it.label }}</p>
          </div>
        </div>

        <!-- Mock bar chart -->
        <div class="mt-6 bg-white rounded-2xl p-6 border border-emerald-100 shadow-sm">
          <div class="flex items-center justify-between mb-4">
            <h4 class="font-bold">Sampah Terkumpul (kg) — 6 Bulan Terakhir</h4>
            <span class="text-xs text-slate-500">2026</span>
          </div>
          <div class="flex items-end gap-3 h-40">
            <div v-for="(v, i) in [4, 7, 5, 9, 11, 12]" :key="i" class="flex-1 flex flex-col items-center gap-2">
              <div class="w-full rounded-t-lg bg-gradient-to-t from-emerald-500 to-emerald-300" :style="{ height: (v * 8) + 'px' }"></div>
              <span class="text-xs text-slate-500">{{ ['Des','Jan','Feb','Mar','Apr','Mei'][i] }}</span>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>