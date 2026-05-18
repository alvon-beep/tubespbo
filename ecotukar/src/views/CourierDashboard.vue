<!--
  CourierDashboard.vue — EcoTukar Courier Dashboard (Mobile-first)
  Target user: Kurir / tukang sampah
  Sections: Header, Daily summary, Smart Routing List, Action cards (Check-in,
            Diangkut, Dibatalkan), Bottom navigation
  Stack: Vue 3 (Composition API) + Tailwind CSS
-->
<script setup>
import { ref } from 'vue'

// Mock daily pickup list, ordered by distance
const pickups = ref([
  { id: 'P-01', user: 'Sarah Putri',  addr: 'Jl. Melati No. 21',   type: 'Plastik', kg: '3 kg', dist: '0.8 km', time: '09:00', status: 'pending' },
  { id: 'P-02', user: 'Andi Wijaya',  addr: 'Jl. Mawar No. 5',     type: 'Kertas',  kg: '5 kg', dist: '1.4 km', time: '09:30', status: 'pending' },
  { id: 'P-03', user: 'Rina Lestari', addr: 'Jl. Anggrek No. 12',  type: 'Kaca',    kg: '8 kg', dist: '2.1 km', time: '10:00', status: 'pending' },
  { id: 'P-04', user: 'Doni Pratama', addr: 'Jl. Kenanga No. 7',   type: 'Logam',   kg: '2 kg', dist: '3.0 km', time: '10:45', status: 'pending' },
  { id: 'P-05', user: 'Maya Anggun',  addr: 'Jl. Cempaka No. 9',   type: 'Plastik', kg: '4 kg', dist: '4.2 km', time: '11:30', status: 'pending' },
])

function setStatus(id, status) {
  const p = pickups.value.find(x => x.id === id)
  if (p) p.status = status
}

function statusBadge(s) {
  return {
    pending:   { t: 'Menunggu',   c: 'bg-amber-100 text-amber-700' },
    checkin:   { t: 'Check-in',   c: 'bg-sky-100 text-sky-700' },
    diangkut:  { t: 'Diangkut',   c: 'bg-emerald-100 text-emerald-700' },
    dibatalkan:{ t: 'Dibatalkan', c: 'bg-rose-100 text-rose-700' },
  }[s]
}
</script>

<template>
  <!-- Mobile-first container, centered on larger screens -->
  <div class="min-h-screen bg-emerald-50/60 font-sans text-slate-800">
    <div class="max-w-md mx-auto pb-24">
      <!-- ===== Header ===== -->
      <header class="bg-gradient-to-br from-emerald-600 to-green-700 text-white p-5 rounded-b-3xl shadow-lg">
        <div class="flex items-center justify-between">
          <div class="flex items-center gap-3">
            <div class="w-10 h-10 rounded-full bg-white/20 grid place-items-center text-xl">👷</div>
            <div>
              <p class="text-xs opacity-80">Selamat pagi,</p>
              <p class="font-bold">Budi Santoso</p>
            </div>
          </div>
          <button class="w-9 h-9 rounded-full bg-white/20 grid place-items-center">🔔</button>
        </div>

        <!-- Daily summary -->
        <div class="mt-5 grid grid-cols-3 gap-2 text-center">
          <div class="bg-white/15 rounded-xl py-3 backdrop-blur">
            <p class="text-lg font-extrabold">5</p>
            <p class="text-[10px] uppercase opacity-80">Pickup</p>
          </div>
          <div class="bg-white/15 rounded-xl py-3 backdrop-blur">
            <p class="text-lg font-extrabold">22 kg</p>
            <p class="text-[10px] uppercase opacity-80">Estimasi</p>
          </div>
          <div class="bg-white/15 rounded-xl py-3 backdrop-blur">
            <p class="text-lg font-extrabold">11.5 km</p>
            <p class="text-[10px] uppercase opacity-80">Rute</p>
          </div>
        </div>
      </header>

      <!-- ===== Smart Routing List + Action Cards ===== -->
      <section class="p-4">
        <div class="flex items-center justify-between mb-3">
          <h2 class="font-bold">🗺️ Rute Hari Ini</h2>
          <span class="text-xs text-slate-500">Diurutkan jarak terdekat</span>
        </div>

        <div class="space-y-3">
          <article
            v-for="(p, i) in pickups"
            :key="p.id"
            class="bg-white rounded-2xl p-4 border border-emerald-100 shadow-sm"
          >
            <!-- Address summary -->
            <div class="flex items-start gap-3">
              <div class="w-9 h-9 rounded-full bg-emerald-600 text-white grid place-items-center font-bold text-sm">
                {{ i + 1 }}
              </div>
              <div class="flex-1">
                <div class="flex items-center justify-between">
                  <p class="font-bold">{{ p.user }}</p>
                  <span :class="['text-[10px] px-2 py-0.5 rounded-full font-semibold', statusBadge(p.status).c]">
                    {{ statusBadge(p.status).t }}
                  </span>
                </div>
                <p class="text-xs text-slate-500 mt-0.5">📍 {{ p.addr }}</p>
                <div class="mt-2 flex flex-wrap gap-2 text-[11px]">
                  <span class="px-2 py-1 rounded-md bg-emerald-50 text-emerald-700 font-semibold">{{ p.type }} • {{ p.kg }}</span>
                  <span class="px-2 py-1 rounded-md bg-slate-100 text-slate-600">⏱️ {{ p.time }}</span>
                  <span class="px-2 py-1 rounded-md bg-slate-100 text-slate-600">🛵 {{ p.dist }}</span>
                </div>
              </div>
            </div>

            <!-- Verification & status actions -->
            <div class="mt-4 grid grid-cols-3 gap-2">
              <button
                @click="setStatus(p.id, 'checkin')"
                class="py-2 rounded-xl text-xs font-semibold bg-sky-100 text-sky-700 hover:bg-sky-200 active:scale-95 transition"
              >
                📍 Check-in
              </button>
              <button
                @click="setStatus(p.id, 'diangkut')"
                class="py-2 rounded-xl text-xs font-semibold bg-emerald-600 text-white hover:bg-emerald-700 active:scale-95 transition"
              >
                ✅ Diangkut
              </button>
              <button
                @click="setStatus(p.id, 'dibatalkan')"
                class="py-2 rounded-xl text-xs font-semibold bg-rose-100 text-rose-700 hover:bg-rose-200 active:scale-95 transition"
              >
                ✖ Batal
              </button>
            </div>
          </article>
        </div>
      </section>

      <!-- ===== Bottom Navigation ===== -->
      <nav class="fixed bottom-0 inset-x-0 max-w-md mx-auto bg-white border-t border-emerald-100 grid grid-cols-4 py-2 text-[11px] font-semibold text-slate-500">
        <button class="flex flex-col items-center gap-0.5 text-emerald-600">
          <span class="text-lg">🏠</span>Beranda
        </button>
        <button class="flex flex-col items-center gap-0.5">
          <span class="text-lg">🗺️</span>Rute
        </button>
        <button class="flex flex-col items-center gap-0.5">
          <span class="text-lg">📜</span>Riwayat
        </button>
        <button class="flex flex-col items-center gap-0.5">
          <span class="text-lg">👤</span>Profil
        </button>
      </nav>
    </div>
  </div>
</template>