package id.co.bni.remittance_card.dto.get_by_orderingid;

import id.co.bni.remittance_card.dto.beneficiary_registration.Header;
import lombok.Data;

@Data
public class GetBeneficiaryByIdOrdering {

    private Header header;
    private String idcust;
}
