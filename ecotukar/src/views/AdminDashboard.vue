<!--
  AdminDashboard.vue — EcoTukar Admin Panel
  Target user: Pengelola bank sampah
  Sections: Sidebar, Summary report cards, Manajemen Jadwal & Kurir (table),
            Sistem Konversi Koin (form)
  Stack: Vue 3 (Composition API) + Tailwind CSS
-->
<script setup>
import { ref, reactive, computed } from 'vue'

// Mock summary reports
const reports = ref([
  { label: 'Total Sampah Hari Ini', value: '142 kg', delta: '+12%', icon: '♻️' },
  { label: 'Total Sampah Bulan Ini', value: '3.4 Ton', delta: '+8%', icon: '📦' },
  { label: 'Koin Didistribusikan', value: '24,800 🪙', delta: '+5%', icon: '🪙' },
  { label: 'Pengguna Aktif', value: '1,204', delta: '+18%', icon: '👥' },
])

// Mock incoming pickup requests
const requests = ref([
  { id: 'REQ-1041', user: 'Sarah Putri', addr: 'Jl. Melati No. 21', type: 'Plastik', weight: '3 kg', date: '20 Mei', courier: 'Budi S.', status: 'Assigned' },
  { id: 'REQ-1042', user: 'Andi Wijaya', addr: 'Jl. Mawar No. 5',   type: 'Kertas',  weight: '5 kg', date: '20 Mei', courier: 'Belum',   status: 'Pending'  },
  { id: 'REQ-1043', user: 'Rina Lestari',addr: 'Jl. Anggrek No. 12',type: 'Kaca',    weight: '8 kg', date: '21 Mei', courier: 'Eko P.',  status: 'On Route' },
  { id: 'REQ-1044', user: 'Doni Pratama',addr: 'Jl. Kenanga No. 7', type: 'Logam',   weight: '2 kg', date: '21 Mei', courier: 'Budi S.', status: 'Completed'},
  { id: 'REQ-1045', user: 'Maya Anggun', addr: 'Jl. Cempaka No. 9', type: 'Plastik', weight: '4 kg', date: '22 Mei', courier: 'Belum',   status: 'Pending'  },
])

const couriers = ['Budi S.', 'Eko P.', 'Wawan H.', 'Rizky A.']

function statusColor(s) {
  return {
    Pending:   'bg-amber-100 text-amber-700',
    Assigned:  'bg-sky-100 text-sky-700',
    'On Route':'bg-indigo-100 text-indigo-700',
    Completed: 'bg-emerald-100 text-emerald-700',
  }[s] || 'bg-slate-100 text-slate-700'
}

// Coin conversion form
const conv = reactive({
  requestId: 'REQ-1042',
  wasteType: 'Kertas',
  actualWeight: 5,
  ratePerKg: 40,
})
const wasteTypes = ['Plastik', 'Kertas', 'Kaca', 'Logam', 'Organik', 'Elektronik']
const totalCoins = computed(() => conv.actualWeight * conv.ratePerKg)

function submitConv() {
  alert(`Konversi tersimpan: ${conv.requestId} → ${totalCoins.value} koin`)
}
</script>

<template>
  <div class="min-h-screen bg-slate-50 font-sans text-slate-800 flex">
    <!-- ===== Sidebar ===== -->
    <aside class="hidden lg:flex w-64 bg-emerald-900 text-emerald-50 flex-col p-6">
      <div class="flex items-center gap-2 mb-10">
        <div class="w-9 h-9 rounded-xl bg-emerald-500 grid place-items-center text-white font-bold">E</div>
        <span class="font-extrabold text-lg">EcoTukar Admin</span>
      </div>
      <nav class="space-y-2 text-sm font-medium">
        <a class="flex items-center gap-3 px-3 py-2 rounded-lg bg-emerald-700">📊 Dashboard</a>
        <a class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-emerald-800">🗓️ Jadwal & Kurir</a>
        <a class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-emerald-800">🪙 Konversi Koin</a>
        <a class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-emerald-800">📈 Laporan</a>
        <a class="flex items-center gap-3 px-3 py-2 rounded-lg hover:bg-emerald-800">👥 Pengguna</a>
      </nav>
    </aside>

    <!-- ===== Main ===== -->
    <main class="flex-1 p-6 lg:p-10 space-y-8">
      <header>
        <h1 class="text-2xl md:text-3xl font-extrabold">Admin Dashboard</h1>
        <p class="text-slate-500 text-sm">Pantau pickup, kurir, dan distribusi koin.</p>
      </header>

      <!-- ===== Laporan Keseluruhan ===== -->
      <section class="grid grid-cols-2 md:grid-cols-4 gap-4">
        <div v-for="(r, i) in reports" :key="i" class="bg-white rounded-2xl p-5 border border-slate-200 shadow-sm">
          <div class="flex items-center justify-between">
            <div class="text-2xl">{{ r.icon }}</div>
            <span class="text-xs font-semibold text-emerald-600">{{ r.delta }}</span>
          </div>
          <p class="mt-3 text-2xl font-extrabold">{{ r.value }}</p>
          <p class="text-xs text-slate-500 mt-1">{{ r.label }}</p>
        </div>
      </section>

      <!-- ===== Manajemen Jadwal & Kurir ===== -->
      <section class="bg-white rounded-2xl border border-slate-200 shadow-sm">
        <div class="flex items-center justify-between px-6 py-4 border-b border-slate-200">
          <h3 class="font-bold">🗓️ Manajemen Jadwal & Kurir</h3>
          <button class="px-4 py-2 rounded-lg bg-emerald-600 text-white text-sm font-semibold hover:bg-emerald-700">+ Assign Manual</button>
        </div>
        <div class="overflow-x-auto">
          <table class="w-full text-sm">
            <thead class="bg-slate-50 text-slate-600 text-left">
              <tr>
                <th class="px-6 py-3 font-semibold">ID</th>
                <th class="px-6 py-3 font-semibold">Pengguna</th>
                <th class="px-6 py-3 font-semibold">Alamat</th>
                <th class="px-6 py-3 font-semibold">Jenis</th>
                <th class="px-6 py-3 font-semibold">Berat</th>
                <th class="px-6 py-3 font-semibold">Tanggal</th>
                <th class="px-6 py-3 font-semibold">Kurir</th>
                <th class="px-6 py-3 font-semibold">Status</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="r in requests" :key="r.id" class="border-t border-slate-100 hover:bg-emerald-50/40">
                <td class="px-6 py-3 font-mono text-xs">{{ r.id }}</td>
                <td class="px-6 py-3 font-semibold">{{ r.user }}</td>
                <td class="px-6 py-3 text-slate-600">{{ r.addr }}</td>
                <td class="px-6 py-3">{{ r.type }}</td>
                <td class="px-6 py-3">{{ r.weight }}</td>
                <td class="px-6 py-3">{{ r.date }}</td>
                <td class="px-6 py-3">
                  <select class="px-2 py-1 rounded-md border border-slate-200 text-xs">
                    <option>{{ r.courier }}</option>
                    <option v-for="c in couriers" :key="c">{{ c }}</option>
                  </select>
                </td>
                <td class="px-6 py-3">
                  <span :class="['px-2 py-1 rounded-full text-xs font-semibold', statusColor(r.status)]">{{ r.status }}</span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>

      <!-- ===== Sistem Konversi Koin ===== -->
      <section class="grid lg:grid-cols-3 gap-6">
        <div class="lg:col-span-2 bg-white rounded-2xl p-6 border border-slate-200 shadow-sm">
          <h3 class="font-bold mb-4">🪙 Sistem Konversi Koin</h3>
          <form @submit.prevent="submitConv" class="grid md:grid-cols-2 gap-5">
            <div>
              <label class="block text-xs font-semibold text-slate-600 mb-1">ID Permintaan</label>
              <input v-model="conv.requestId" class="w-full px-4 py-2.5 rounded-xl border border-slate-200 focus:ring-2 focus:ring-emerald-400 focus:outline-none" />
            </div>
            <div>
              <label class="block text-xs font-semibold text-slate-600 mb-1">Jenis Sampah</label>
              <select v-model="conv.wasteType" class="w-full px-4 py-2.5 rounded-xl border border-slate-200 focus:ring-2 focus:ring-emerald-400 focus:outline-none">
                <option v-for="w in wasteTypes" :key="w">{{ w }}</option>
              </select>
            </div>
            <div>
              <label class="block text-xs font-semibold text-slate-600 mb-1">Berat Aktual (kg)</label>
              <input v-model.number="conv.actualWeight" type="number" min="0" step="0.1" class="w-full px-4 py-2.5 rounded-xl border border-slate-200 focus:ring-2 focus:ring-emerald-400 focus:outline-none" />
            </div>
            <div>
              <label class="block text-xs font-semibold text-slate-600 mb-1">Rate Koin / kg</label>
              <input v-model.number="conv.ratePerKg" type="number" min="0" class="w-full px-4 py-2.5 rounded-xl border border-slate-200 focus:ring-2 focus:ring-emerald-400 focus:outline-none" />
            </div>
            <div class="md:col-span-2">
              <button type="submit" class="w-full py-3 rounded-xl bg-emerald-600 text-white font-semibold hover:bg-emerald-700 transition">
                Simpan Konversi
              </button>
            </div>
          </form>
        </div>

        <div class="bg-gradient-to-br from-emerald-600 to-green-700 rounded-2xl p-6 text-white shadow-lg flex flex-col justify-center">
          <p class="text-xs uppercase tracking-wider opacity-80">Total Koin Dikonversi</p>
          <p class="text-5xl font-extrabold mt-2">{{ totalCoins.toLocaleString() }} 🪙</p>
          <p class="text-sm mt-3 opacity-90">
            Berdasarkan {{ conv.actualWeight }} kg {{ conv.wasteType }}
            @ {{ conv.ratePerKg }} koin/kg.
          </p>
        </div>
      </section>
    </main>
  </div>
</template>