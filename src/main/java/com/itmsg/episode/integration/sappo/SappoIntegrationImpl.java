package com.itmsg.episode.integration.sappo;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;

import com.itmsg.episode.integration.Integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@WebService(targetNamespace = SappoIntegrationEndpoint.NAMESPACE_URI, portName = SappoIntegrationEndpoint.PORT_NAME, serviceName = SappoIntegrationEndpoint.SERVICE_NAME, endpointInterface = "com.itmsg.episode.integration.sappo.SappoIntegration")
public class SappoIntegrationImpl implements SappoIntegration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IFSappoRepository iFSappoRepository;

    @Override
    public void syncSappo(List<Integration> datas) {
        logger.debug("SappoIntegration syncSappo start");
        iFSappoRepository.deleteAll();
        for(Integration integration:datas){
            createSappo(integration);
        }
    }

    private void createSappo(Integration integration) {
        IFSappo ifSappo = new IFSappo();

        ifSappo.setIcSendObjectId(integration.getCol1());
        ifSappo.setIcSendParty(integration.getCol2()); 
        ifSappo.setIcSendCompnm(integration.getCol3()); 
        ifSappo.setIcSendIfnm(integration.getCol4()); 
        ifSappo.setIcSendIfns(integration.getCol5()); 
        ifSappo.setIcSendChannel(integration.getCol6()); 
        ifSappo.setIcSendAdaptertype(integration.getCol7()); 
        ifSappo.setIcSendDirection(integration.getCol8());
        ifSappo.setIcSendMsgprot(integration.getCol9());
        ifSappo.setIcSendTrnsprot(integration.getCol10());
        ifSappo.setIcRecvObjectId(integration.getCol11());
        ifSappo.setIcRecvParty(integration.getCol12());
        ifSappo.setIcRecvCompnm(integration.getCol13()); 
        ifSappo.setIcRecvIfnm(integration.getCol14()); 
        ifSappo.setIcRecvIfns(integration.getCol15()); 
        ifSappo.setIcRecvChannel(integration.getCol16()); 
        ifSappo.setIcRecvAdaptertype(integration.getCol17()); 
        ifSappo.setIcRecvDirection(integration.getCol18()); 
        ifSappo.setIcRecvMsgprot(integration.getCol19()); 
        ifSappo.setIcRecvTrnsprot(integration.getCol20()); 

        ifSappo.setCreateId("intadmin");
        ifSappo.setSourceSystem("SAPPO");
        ifSappo.setTargetSystem("ICAM");
        ifSappo.setResult(integration.getResult());
        ifSappo.setRemarks(integration.getRemarks());
        iFSappoRepository.save(ifSappo);
    };

}