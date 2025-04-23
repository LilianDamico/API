package com.mindcare.api.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.mindcare.api.dto.RelatorioDTO;
import com.mindcare.api.model.MedicalRecord;
import com.mindcare.api.model.Prescription;
import com.mindcare.api.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class RelatorioService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public Optional<RelatorioDTO> gerarRelatorioPorId(Long id) {
        Optional<MedicalRecord> registro = medicalRecordRepository.findById(id);

        if (registro.isEmpty()) return Optional.empty();

        MedicalRecord r = registro.get();
        RelatorioDTO dto = new RelatorioDTO();

        dto.nomePaciente = r.getPatient().getNome();
        dto.emailPaciente = r.getPatient().getEmail();
        dto.nomeProfissional = r.getProfessional().getNome();
        dto.data = r.getData();
        dto.diagnostico = r.getDiagnostico();
        dto.observacoes = r.getObservacoes();

        if (r.getPrescription() != null) {
            Prescription p = r.getPrescription();
            dto.descricaoMedicamento = p.getDescricao();
            dto.indicacoes = p.getIndicacoes();
            dto.contraIndicacoes = p.getContraIndicacoes();
            dto.efeitosColaterais = p.getEfeitosColaterais();
        }

        return Optional.of(dto);
    }

    public ResponseEntity<byte[]> gerarPdfPorRegistro(Long id) {
        Optional<RelatorioDTO> opt = gerarRelatorioPorId(id);

        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RelatorioDTO dto = opt.get();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);
            doc.open();

            Font titulo = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font texto = new Font(Font.HELVETICA, 12);

            doc.add(new Paragraph("Relatório Médico Consolidado", titulo));
            doc.add(new Paragraph(" "));

            doc.add(new Paragraph("Paciente: " + dto.nomePaciente, texto));
            doc.add(new Paragraph("Email: " + dto.emailPaciente, texto));
            doc.add(new Paragraph("Profissional: " + dto.nomeProfissional, texto));
            doc.add(new Paragraph("Data: " + dto.data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), texto));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Diagnóstico: " + dto.diagnostico, texto));
            doc.add(new Paragraph("Observações: " + dto.observacoes, texto));
            doc.add(new Paragraph(" "));

            if (dto.descricaoMedicamento != null) {
                doc.add(new Paragraph("Medicamento: " + dto.descricaoMedicamento, texto));
                doc.add(new Paragraph("Indicações: " + dto.indicacoes, texto));
                doc.add(new Paragraph("Contraindicações: " + dto.contraIndicacoes, texto));
                doc.add(new Paragraph("Efeitos colaterais: " + dto.efeitosColaterais, texto));
            }

            doc.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "relatorio_" + id + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(baos.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
