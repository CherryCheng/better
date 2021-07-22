package top.cherrycheng.study.test.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeVersion implements Serializable {

    private static final long serialVersionUID = 6219893060794022254L;
    private String value;
}
