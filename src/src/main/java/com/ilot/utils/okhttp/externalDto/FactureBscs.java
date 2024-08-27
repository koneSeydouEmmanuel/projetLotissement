package com.ilot.utils.okhttp.externalDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
@NoArgsConstructor
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FactureBscs {
    private String compteClient;
    private String dateEcheance;
    private String dateFacture;
    private Float  montantDu;
    private Float  montantFacture;
    private String numeroFacture;
}
