package top.cherrycheng.better.fileoperation.controller;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cherrycheng.better.fileoperation.service.PDFService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ChengRu
 * @date 2020/2/28 7:37
 * @Desc
 */
@RestController
@RequestMapping("/fileoperation")
public class PDFController {
    @Autowired
    private PDFService pdfService;

    /**
     * HTML转PDF
     */
    @RequestMapping(value = "/html2pdf")
//    @CrossOrigin //用于解决跨域
    public void html2pdf(HttpServletResponse response) throws IOException, DocumentException {

        byte[] bytes = pdfService.html2PdfBytes();
        response.setContentType("application/pdf;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();

    }
}
