/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.fhir.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Controller
@RequestMapping(value = "/fhir/")
public class FHIRResourceController  {

    @RequestMapping(value = "/{resource}", method = RequestMethod.GET)
    @ResponseBody
    public Object search(@PathVariable("resource") String resource,
                         @RequestParam(value = "identifier", required = false) String identifier,
                         HttpServletRequest request, HttpServletResponse response) throws Exception {

        String result = null;

        if(resource.equals("Observation")){
            FHIRObservationResource observationResource = new FHIRObservationResource();
            result = observationResource.doSearch(request);
        }

        if(resource.equals("Patient")){
            System.out.println(identifier);
            FHIRPatientResource patientResource = new FHIRPatientResource();
            result = patientResource.searchByIdentifier(identifier, request.getContentType());
        }

        System.out.println(result);
        return result;
    }


    @RequestMapping(value = "/{resource}/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public Object retrieve(@PathVariable("resource") String resource, @PathVariable("uuid") String uuid,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        String result = null;

        if(resource.equals("Patient")){
            FHIRPatientResource patientResource = new FHIRPatientResource();
            result = patientResource.getByUniqueId(uuid, request.getContentType());
        }

        if(resource.equals("Practitioner")){
            FHIRPractitionerResource practitionerResource = new FHIRPractitionerResource();
            result = practitionerResource.getByUniqueId(uuid, request.getContentType());
        }

        if(resource.equals("Observation")){
            FHIRObservationResource observationResource = new FHIRObservationResource();
            result = (String) observationResource.retrieve(uuid, request);
        }

        if(resource.equals("FamilyHistory")){
            FHIRFamilyHistoryResource familyHistoryResource = new FHIRFamilyHistoryResource();
            result = (String)familyHistoryResource.retrieve(uuid, request);
        }

        return result;
    }


}