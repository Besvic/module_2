package com.epam.esm.dao;

import java.util.List;

public interface GiftCertificateTagDao {

    int[] addTagToCertificate(long idCertificate, List<Long> tagIdList);

}
