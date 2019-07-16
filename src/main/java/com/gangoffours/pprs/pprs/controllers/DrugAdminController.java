package com.gangoffours.pprs.pprs.controllers;

import com.gangoffours.pprs.pprs.common.AuthControllerBase;
import com.gangoffours.pprs.pprs.models.DrugScalar;
import com.gangoffours.pprs.pprs.models.DrugScalarKey;
import com.gangoffours.pprs.pprs.repositories.IDrugScalarRepository;
import com.gangoffours.pprs.pprs.repositories.IUserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/admin/drugs")
public class DrugAdminController extends AuthControllerBase {
    private final IDrugScalarRepository _drugScalarRepository;

    public DrugAdminController(IUserRepository userRepository, IDrugScalarRepository drugScalarRepository) {
        super(userRepository);
        _drugScalarRepository = drugScalarRepository;
    }

    private void process(File file) throws IOException {
        String fileName = file.getName();
        ArrayList<DrugScalar> drugScalars = new ArrayList<DrugScalar>();

        FileReader fr = null;
        BufferedReader br = null;

        try
        {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
           
            String line;
            while((line = br.readLine()) != null){
                //process the line
                String[] tokens = Arrays
                    .stream(line.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);
                    drugScalars.add(new DrugScalar(
                        new DrugScalarKey(fileName, tokens[0]),
                        Double.parseDouble(tokens[1])
                    ));
            }

            _drugScalarRepository.saveAll(drugScalars);
        }
        finally
        {
            if (br != null) br.close();
            if (fr != null) fr.close();
        }
    }

    @GetMapping("/importdata")
    public Object getIndex() throws IOException {
        File dir = new File("C:/src/GangOfFours/docs/DrugVectors");
        File[] directoryListing = dir.listFiles();
        if (directoryListing == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        Arrays
            .stream(directoryListing)
            .forEach(t -> {
                    try {
                        process(t);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}