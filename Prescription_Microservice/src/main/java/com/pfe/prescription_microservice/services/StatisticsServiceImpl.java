package com.pfe.prescription_microservice.services;

import com.pfe.prescription_microservice.entities.MedicalService;
import com.pfe.prescription_microservice.entities.Medicament;
import com.pfe.prescription_microservice.repositories.PrescriptionRepository;
import com.pfe.prescription_microservice.repositories.PatientRepository;
import com.pfe.prescription_microservice.repositories.MedicamentRepository;
import com.pfe.prescription_microservice.entities.Prescription;
import com.pfe.prescription_microservice.entities.PrescriptionMedicament;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;
    private final MedicamentRepository medicamentRepository;

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("newPrescriptions", countNewPrescriptions());
        stats.put("totalPrescriptions", countTotalPrescriptions());
        stats.put("totalPatients", countTotalPatients());
        stats.put("totalMedicaments", countTotalMedicaments());
        stats.put("totalPrescriptionCost", totalPrescriptionCost());
        stats.put("prescriptionsByMonth", getPrescriptionsByMonth());
        stats.put("getPrescriptionsByRegion", getPrescriptionsByRegion());
        stats.put("lastMonthSales", getLastMonthSales());
        stats.put("lastWeekSales", getLastWeekSales());
        stats.put("satisfactionRatesByService", getSatisfactionRatesByService()); // Ajouter cette ligne
        return stats;
    }

    private long countNewPrescriptions() {
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        return prescriptionRepository.findAll().stream()
                .filter(p -> p.getDatePrescription().isAfter(oneWeekAgo))
                .count();
    }

    private long countTotalPrescriptions() {
        return prescriptionRepository.count();
    }

    private long countTotalPatients() {
        return patientRepository.count();
    }

    private long countTotalMedicaments() {
        return medicamentRepository.count();
    }

    private double totalPrescriptionCost() {
        return prescriptionRepository.findAll().stream().mapToDouble(Prescription::getCost).sum();
    }

    private Map<String, Long> getPrescriptionsByMonth() {
        Map<String, Long> monthlyPrescriptions = new HashMap<>();
        for (int month = 1; month <= 12; month++) {
            YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), month);
            LocalDate start = yearMonth.atDay(1);
            LocalDate end = yearMonth.atEndOfMonth();
            long count = prescriptionRepository.findAll().stream()
                    .filter(p -> !p.getDatePrescription().isBefore(start) && !p.getDatePrescription().isAfter(end))
                    .count();
            monthlyPrescriptions.put(yearMonth.getMonth().name(), count);
        }
        return monthlyPrescriptions;
    }

    public Map<String, Long> getPrescriptionsByRegion() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();
        Map<String, Long> prescriptionsByRegion = new HashMap<>();

        for (Prescription prescription : prescriptions) {
            if (prescription.getPatient() != null) {
                String adresse = prescription.getPatient().getAdresse();
                if (adresse != null && !adresse.isEmpty()) {
                    String[] addressParts = adresse.split(" ");
                    String region = addressParts[addressParts.length - 1];
                    prescriptionsByRegion.put(region, prescriptionsByRegion.getOrDefault(region, 0L) + 1);
                }
            }
        }

        return prescriptionsByRegion;
    }

    private double getLastMonthSales() {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        LocalDate start = lastMonth.atDay(1);
        LocalDate end = lastMonth.atEndOfMonth();
        return prescriptionRepository.findAll().stream()
                .filter(p -> !p.getDatePrescription().isBefore(start) && !p.getDatePrescription().isAfter(end))
                .mapToDouble(Prescription::getCost)
                .sum();
    }

    private Map<String, Double> getLastWeekSales() {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusWeeks(1).plusDays(1);

        Map<String, Double> dailySales = new LinkedHashMap<>();

        // Initialiser les jours de la semaine avec 0
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            dailySales.put(date.getDayOfWeek().toString(), 0.0);
        }

        // Calculer les ventes pour chaque jour
        List<Prescription> prescriptions = prescriptionRepository.findAll().stream()
                .filter(p -> !p.getDatePrescription().isBefore(start) && !p.getDatePrescription().isAfter(end))
                .collect(Collectors.toList());

        for (Prescription prescription : prescriptions) {
            String dayOfWeek = prescription.getDatePrescription().getDayOfWeek().toString();
            double cost = dailySales.getOrDefault(dayOfWeek, 0.0);
            dailySales.put(dayOfWeek, cost + prescription.getCost());
        }

        return dailySales;
    }

    private Map<MedicalService, Double> getSatisfactionRatesByService() {
        Map<MedicalService, Double> satisfactionRates = new HashMap<>();
        for (MedicalService service : MedicalService.values()) {
            List<Prescription> prescriptions = prescriptionRepository.findByMedicalService(service);
            Map<Medicament, Integer> totalRequestedMap = new HashMap<>();
            Map<Medicament, Integer> totalAvailableMap = new HashMap<>();

            for (Prescription prescription : prescriptions) {
                for (PrescriptionMedicament pm : prescription.getPrescriptionMedicaments()) {
                    totalRequestedMap.put(pm.getMedicament(),
                            totalRequestedMap.getOrDefault(pm.getMedicament(), 0) + pm.getQuantite());
                    totalAvailableMap.put(pm.getMedicament(), pm.getMedicament().getQuantite());
                }
            }

            double totalRequested = totalRequestedMap.values().stream().mapToInt(Integer::intValue).sum();
            double totalAvailable = totalAvailableMap.values().stream().mapToInt(Integer::intValue).sum();

            double satisfactionRate = (totalRequested == 0) ? 100 : (Math.min(totalAvailable, totalRequested) / totalRequested) * 100;
            satisfactionRates.put(service, satisfactionRate);
        }
        return satisfactionRates;
    }
}
