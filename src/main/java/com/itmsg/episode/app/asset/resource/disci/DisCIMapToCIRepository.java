
package com.itmsg.episode.app.asset.resource.disci;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DisCIMapToCIRepository extends JpaRepository<DisCIMapToCI, Long> {
    DisCIMapToCI findByDataSrcAndDisCITypeAndDisCIClassAndIsVirtual(String dataSrc,String disCIType,String disCIClass,Boolean isVirtual);

}