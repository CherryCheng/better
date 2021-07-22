package top.cherrycheng.study.test.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferenceCode implements Serializable {

    private static final long serialVersionUID = 7888630192701482563L;
    private String value;
}
