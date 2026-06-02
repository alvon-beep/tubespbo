package com.ecotukar.service;

import com.ecotukar.model.*;
import com.ecotukar.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EcoTukarService {
    private final AkunRepository akunRepo;
    private final ProfilCustomerRepository customerRepo;
    private final ProfilTukangRepository tukangRepo;
    private final ProfilAdminRepository adminRepo;
    private final RequestSampahRepository requestRepo;
    private final PermintaanPenjemputanRepository verifikasiRepo;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public EcoTukarService(AkunRepository akunRepo,
                           ProfilCustomerRepository customerRepo,
                           ProfilTukangRepository tukangRepo,
                           ProfilAdminRepository adminRepo,
                           RequestSampahRepository requestRepo,
                           PermintaanPenjemputanRepository verifikasiRepo) {
        this.akunRepo = akunRepo;
        this.customerRepo = customerRepo;
        this.tukangRepo = tukangRepo;
        this.adminRepo = adminRepo;
        this.requestRepo = requestRepo;
        this.verifikasiRepo = verifikasiRepo;
    }

    // ==================== AUTHENTICATION WORKFLOWS ====================

    public Akun authenticate(String email, String password) {
        Optional<Akun> optAkun = akunRepo.findByEmail(email);
        if (optAkun.isPresent()) {
            Akun akun = optAkun.get();
            // Matching password hash/raw text as in the original repository seeds
            if (akun.getPasswordHash().equals(password)) {
                return akun;
            }
        }
        return null;
    }

    @Transactional
    public Akun registerCustomer(String namaLengkap, String noTelepon, String email, String password) {
        if (akunRepo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email sudah terdaftar.");
        }
        
        Akun akun = new Akun(email, password, "CUSTOMER");
        akun = akunRepo.save(akun);

        ProfilCustomer profile = new ProfilCustomer(akun.getIdAkun(), namaLengkap, noTelepon, "");
        customerRepo.save(profile);

        return akun;
    }

    @Transactional
    public Akun registerAdmin(String namaLengkap, String noTelepon, String email, String password) {
        if (akunRepo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email sudah terdaftar.");
        }

        Akun akun = new Akun(email, password, "ADMIN");
        akun = akunRepo.save(akun);

        ProfilAdmin profile = new ProfilAdmin(akun.getIdAkun(), namaLengkap, noTelepon);
        adminRepo.save(profile);

        return akun;
    }

    @Transactional
    public Akun registerTukang(String namaLengkap, String noTelepon, String email, String password) {
        if (akunRepo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email sudah terdaftar.");
        }

        Akun akun = new Akun(email, password, "TUKANG");
        akun = akunRepo.save(akun);

        ProfilTukang profile = new ProfilTukang(akun.getIdAkun(), namaLengkap, noTelepon);
        tukangRepo.save(profile);

        return akun;
    }

    // ==================== PROFILE RESOLUTION ====================

    public User getUserByUsername(String username) {
        // JSP requests sarah by default. We'll search in accounts.
        Optional<Akun> optAkun = akunRepo.findByEmail(username);
        if (!optAkun.isPresent()) {
            // Check if it matches an email or look up by name in customer profile
            optAkun = akunRepo.findAll().stream()
                    .filter(a -> a.getEmail().split("@")[0].equalsIgnoreCase(username))
                    .findFirst();
        }

        if (optAkun.isPresent()) {
            return convertAkunToUser(optAkun.get());
        }
        return null;
    }

    public User convertAkunToUser(Akun akun) {
        String name = akun.getEmail().split("@")[0];
        String address = "Alamat Belum Set";
        String avatar = "👤";
        String joined = "Juni 2026";
        int points = 0;

        if ("CUSTOMER".equals(akun.getRole())) {
            avatar = "👩‍🦰";
            Optional<ProfilCustomer> cust = customerRepo.findByIdAkun(akun.getIdAkun());
            if (cust.isPresent()) {
                name = cust.get().getNamaLengkap();
                address = cust.get().getAlamatLengkap();
                points = cust.get().getSaldoPoin();
            }
        } else if ("TUKANG".equals(akun.getRole())) {
            avatar = "👷";
            Optional<ProfilTukang> tkg = tukangRepo.findByIdAkun(akun.getIdAkun());
            if (tkg.isPresent()) {
                name = tkg.get().getNamaLengkap();
                address = "Hub Lapangan";
            }
        } else if ("ADMIN".equals(akun.getRole())) {
            avatar = "💼";
            Optional<ProfilAdmin> adm = adminRepo.findByIdAkun(akun.getIdAkun());
            if (adm.isPresent()) {
                name = adm.get().getNamaLengkap();
                address = "Kantor Pusat Bank Sampah";
            }
        }

        return new User(akun.getEmail(), name, akun.getEmail(), akun.getRole(), avatar, address, joined, points);
    }

    // ==================== WASTE PICKUP OPERATIONS ====================

    public List<PickupRequest> getPickups() {
        List<RequestSampah> dbRequests = requestRepo.findAll(Sort.by(Sort.Direction.DESC, "idRequest"));
        List<PickupRequest> list = new ArrayList<>();
        for (RequestSampah req : dbRequests) {
            list.add(mapToPickupRequest(req));
        }
        return list;
    }

    public PickupRequest mapToPickupRequest(RequestSampah req) {
        String username = "";
        String customerName = "Pengguna";
        
        Optional<Akun> custAkun = akunRepo.findById(req.getIdCustomer());
        if (custAkun.isPresent()) {
            username = custAkun.get().getEmail();
            Optional<ProfilCustomer> custProf = customerRepo.findByIdAkun(custAkun.get().getIdAkun());
            if (custProf.isPresent()) {
                customerName = custProf.get().getNamaLengkap();
            }
        }

        String courierName = "Belum";
        double actualWeight = 0.0;
        Optional<PermintaanPenjemputan> verif = verifikasiRepo.findByIdRequest(req.getIdRequest());
        if (verif.isPresent()) {
            actualWeight = verif.get().getBeratAktual();
            Optional<Akun> courierAkun = akunRepo.findById(verif.get().getIdTukang());
            if (courierAkun.isPresent()) {
                Optional<ProfilTukang> tkgProf = tukangRepo.findByIdAkun(courierAkun.get().getIdAkun());
                if (tkgProf.isPresent()) {
                    courierName = tkgProf.get().getNamaLengkap();
                }
            }
        }

        String dateStr = req.getTanggalDibuat() != null ? req.getTanggalDibuat().format(DATE_FORMATTER) : "Hari Ini";

        PickupRequest pr = new PickupRequest(
            String.valueOf(req.getIdRequest()),
            username,
            customerName,
            req.getAlamatPenjemputan(),
            req.getJenisSampah(),
            req.getBeratEstimasi(),
            dateStr,
            "", // note placeholder
            courierName,
            req.getStatus(),
            "12:00"
        );
        pr.setActualWeight(actualWeight);
        return pr;
    }

    @Transactional
    public PickupRequest addPickup(PickupRequest pr) {
        // Resolve customer account ID by searching email
        Optional<Akun> custAkun = akunRepo.findByEmail(pr.getUsername());
        Integer custId = custAkun.isPresent() ? custAkun.get().getIdAkun() : 31; // Fallback seed customer ID if mock
        
        RequestSampah rs = new RequestSampah(
            custId,
            pr.getWasteType(),
            pr.getEstimatedWeight(),
            pr.getAddress()
        );
        rs = requestRepo.save(rs);
        return mapToPickupRequest(rs);
    }

    @Transactional
    public void assignCourier(String id, String courierName) {
        Integer idRequest = Integer.parseInt(id);
        Optional<RequestSampah> optReq = requestRepo.findById(idRequest);
        if (optReq.isPresent()) {
            RequestSampah rs = optReq.get();
            if ("PENDING".equals(rs.getStatus())) {
                rs.setStatus("ASSIGNED");
                requestRepo.save(rs);
            }

            // Find courier account by full name in profil_tukang
            Optional<ProfilTukang> courier = tukangRepo.findAll().stream()
                    .filter(t -> t.getNamaLengkap().equalsIgnoreCase(courierName))
                    .findFirst();
            
            if (courier.isPresent()) {
                Integer courierAkunId = courier.get().getIdAkun();
                // Check if verification record already exists
                Optional<PermintaanPenjemputan> optVer = verifikasiRepo.findByIdRequest(idRequest);
                if (!optVer.isPresent()) {
                    PermintaanPenjemputan pp = new PermintaanPenjemputan(idRequest, courierAkunId);
                    verifikasiRepo.save(pp);
                } else {
                    PermintaanPenjemputan pp = optVer.get();
                    pp.setIdTukang(courierAkunId);
                    verifikasiRepo.save(pp);
                }
            }
        }
    }

    @Transactional
    public void updateStatus(String id, String status) {
        Integer idRequest = Integer.parseInt(id);
        Optional<RequestSampah> optReq = requestRepo.findById(idRequest);
        if (optReq.isPresent()) {
            RequestSampah rs = optReq.get();
            rs.setStatus(status.toUpperCase());
            requestRepo.save(rs);
        }
    }

    @Transactional
    public void convertCoins(String id, double actualWeight, int ratePerKg) {
        Integer idRequest = Integer.parseInt(id);
        Optional<RequestSampah> optReq = requestRepo.findById(idRequest);
        if (optReq.isPresent()) {
            RequestSampah rs = optReq.get();
            rs.setStatus("COMPLETED");
            requestRepo.save(rs);

            // Update PermintaanPenjemputan record
            Optional<PermintaanPenjemputan> optPp = verifikasiRepo.findByIdRequest(idRequest);
            if (optPp.isPresent()) {
                PermintaanPenjemputan pp = optPp.get();
                pp.setBeratAktual(actualWeight);
                verifikasiRepo.save(pp);
            }

            // Reward points to customer
            int pointsEarned = (int) (actualWeight * ratePerKg);
            Optional<ProfilCustomer> optCust = customerRepo.findByIdAkun(rs.getIdCustomer());
            if (optCust.isPresent()) {
                ProfilCustomer pc = optCust.get();
                pc.setSaldoPoin(pc.getSaldoPoin() + pointsEarned);
                customerRepo.save(pc);
            }
        }
    }

    // ==================== TRANSACTION HISTORY ====================

    public List<WalletTransaction> getTransactions() {
        // Return transaction logs dynamically based on COMPLETED status in database
        List<RequestSampah> completed = requestRepo.findByStatusOrderByIdRequestDesc("COMPLETED");
        List<WalletTransaction> list = new ArrayList<>();
        
        for (RequestSampah rs : completed) {
            double wt = rs.getBeratEstimasi();
            Optional<PermintaanPenjemputan> pp = verifikasiRepo.findByIdRequest(rs.getIdRequest());
            if (pp.isPresent() && pp.get().getBeratAktual() > 0) {
                wt = pp.get().getBeratAktual();
            }
            
            int pts = (int) (wt * 50); // Default conversion multiplier
            String dateStr = rs.getTanggalDibuat() != null ? rs.getTanggalDibuat().format(DateTimeFormatter.ofPattern("dd MMM yyyy")) : "Hari Ini";
            
            list.add(new WalletTransaction(
                "Pickup " + rs.getJenisSampah() + " " + wt + "kg",
                dateStr,
                "+" + pts
            ));
        }

        // Add some default baseline redemptions if history is short to match visual style
        if (list.isEmpty()) {
            list.add(new WalletTransaction("Tukar Voucher Belanja", "08 Mei 2026", "-300"));
            list.add(new WalletTransaction("Bonus Referral", "01 Mei 2026", "+50"));
        }
        return list;
    }
}
