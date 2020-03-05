package top.cherrycheng.better.fileoperation.service;

import com.google.common.base.Charsets;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author ChengRu
 * @date 2020/2/28 7:38
 * @Desc
 */
@Component
public class PDFService {
    /**
     * pdf字体包存放位置:
     * 如:/data/conf/simsun.ttc
     * 如果配置了，取这个，
     * 没配置取\resources\simsun.ttc
     */
    @Value("${pdfFontPath:}")
    private String pdfFontPath;

    public byte[] html2PdfBytes() throws IOException, DocumentException {
        String html = buildHTMLWithParams();
        if (StringUtils.isEmpty(html)) {
            throw new RuntimeException("HTML模板未找到");
        }
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont(getPDFFontPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(html);
        renderer.layout();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        renderer.createPDF(baos);
        renderer.finishPDF();
        return baos.toByteArray();
    }

    /**
     * 构建结清证明的HTML参数
     */
    private String buildHTMLWithParams() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/file/settlement_proof.html");
        String html = StreamUtils.copyToString(stream, Charsets.UTF_8);
        html = html.replace("${name}", "张三");
        html = html.replace("${sex}", "女");
        html = html.replace("${cerNo}", "222222222222222222");
        html = html.replace("${grantActualDate}"
                , DateFormatUtils.format(new Date(), "yyyy年MM月dd日"));
        html = html.replace("${productName}", "产品名");
        html = html.replace("${loanAmount}", "222.00");
        html = html.replace("${term}", "3");
        html = html.replace("${assetNo}", "8888888888888888");
        html = html.replace("${clearDate}"
                , DateFormatUtils.format(new Date(), "yyyy年MM月dd日"));
        html = html.replace("${nowDate}"
                , DateFormatUtils.format(new Date(), "yyyy年MM月dd日"));
        return html;
    }

    private String getPDFFontPath() throws IOException {
        if (StringUtils.isNotEmpty(pdfFontPath)) {
            return pdfFontPath;
        }
        final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        final Resource resource = resolver.getResource("classpath:file/simsun.ttc");
        final String protocol = resource.getURL().getProtocol();
        String path = resource.getURL().getPath();
        if (protocol.equals("jar")) {
//			兼容linux平台，linux下protocol是jar，要用jar:去取。windows下protocol是file,可直接取。
            return "jar:" + path;
        }
        return path;
    }
}
