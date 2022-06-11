package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.edi.models.common.N3;
import com.brihaspathee.zeus.edi.models.common.N4;
import com.brihaspathee.zeus.edi.models.common.NM1;
import com.brihaspathee.zeus.edi.models.common.PER;
import com.brihaspathee.zeus.edi.models.enrollment.Loop2100G;
import com.brihaspathee.zeus.helper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 23, May 2022
 * Time: 3:17 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Loop2100GDataHelperImpl implements Loop2100GDataHelper {

    /**
     * Populates and creates NM1 segment
     */
    private final NM1SegmentHelper nm1SegmentHelper;

    /**
     * Populates and creates PER segment
     */
    private final PERSegmentHelper perSegmentHelper;

    /**
     * Populates and creates N3 segment
     */
    private final N3SegmentHelper n3SegmentHelper;

    /**
     * Populates and creates N4 segment
     */
    private final N4SegmentHelper n4SegmentHelper;

    /**
     * Parses through the segments of all the Loop 2100Gs and creates the set of
     * individual loop 2100G object
     * @param loop2100GValues
     * @return
     */
    @Override
    public Set<Loop2100G> populateLoop2100G(List<String> loop2100GValues) {
        // Responsible Person is not always present in the transaction
        // populate as NULL if not present in the transaction
        if(loop2100GValues.isEmpty()){
            return null;
        }
        // There can be more than one responsible person loop, so all the details will be captured in the set
        Set<Loop2100G> responsiblePersons = new HashSet<>();
        // This list will contain the segments (NM1, PER, N3 and N4) of one responsible person
        // This list will be cleared after each responsible person segment is formed and added to the set
        List<String> responsiblePersonData = new ArrayList<>();
        for(String responsiblePersonSegment: loop2100GValues){
            // Trim of white spaces
            responsiblePersonSegment=responsiblePersonSegment.trim();
            List<String> elements = Arrays.asList(responsiblePersonSegment.split("\\*"));
            // Get the segment name
            String segmentName = elements.get(0);
            // Responsible Person begins with NM1 segment
            if(segmentName.equals("NM1")){
                // If the segment is NM1, then a responsible person is available
                if(!responsiblePersonData.isEmpty()){
                    // If the responsible person list is not empty then there was a previous responsible person segments
                    // these segments have to be parsed and the Loop2100G object has to be created by invoking
                    // the populateResponsiblePerson method
                    Loop2100G responsiblePerson = populateResponsiblePerson(responsiblePersonData);
                    // Once the Loop2100D object is created, it is added to the responsiblePersons set
                    responsiblePersons.add(responsiblePerson);
                    // Once added to the set the responsiblePersonData List is cleared so that the next responsible
                    // person can be created
                    responsiblePersonData.clear();
                }
            }
            responsiblePersonData.add(responsiblePersonSegment);
        }
        Loop2100G loop2100G = populateResponsiblePerson(responsiblePersonData);
        responsiblePersons.add(loop2100G);
        //log.info("Responsible Persons:{}", responsiblePersons);
        return responsiblePersons;
    }

    /**
     * This method takes all the segments of a single Loop 2100G and creates the Loop2100G object
     * @param responsiblePersonData
     * @return
     */
    private Loop2100G populateResponsiblePerson(List<String> responsiblePersonData){
        Loop2100G loop2100G = Loop2100G.builder().build();
        for(String responsiblePersonSegment: responsiblePersonData){
            responsiblePersonSegment=responsiblePersonSegment.trim();
            List<String> elements = Arrays.asList(responsiblePersonSegment.split("\\*"));
            String segmentName = elements.get(0);
            switch (segmentName){
                case "NM1":
                    NM1 name = nm1SegmentHelper.populateNM1Segment(elements);
                    loop2100G.setResponsiblePersonName(name);
                    break;
                case "PER":
                    PER communication = perSegmentHelper.populatePERSegment(elements);
                    loop2100G.setResponsiblePersonCommunications(communication);
                    break;
                case "N3":
                    N3 addressLines = n3SegmentHelper.populateN3Segment(elements);
                    loop2100G.setResponsiblePersonAddressLine(addressLines);
                    break;
                case "N4":
                    N4 cityStateZip = n4SegmentHelper.populateN4Segment(elements);
                    loop2100G.setResponsiblePersonCityStateZip(cityStateZip);
                    break;
            }
        }
        return loop2100G;
    }
}
