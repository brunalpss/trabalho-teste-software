package alugueldecarros.enums;

import java.io.Serializable;


public enum OrderStatusEnum implements Serializable {
    CREATED("CRIADO"),
    ANALISYS("EM_ANALISE"),
    APROVED("APROVADO"),
    REPROVED("RECUSADO");

    private final String code;

    OrderStatusEnum(String code) {
        this.code = code;
    }

    public static OrderStatusEnum getByCd(String cd) {
        for(OrderStatusEnum e : values()) {
            if(e.code.equals(cd)) return e;
        }
        return null;
    }

    public String getCode() {
        return code;
    }
}

