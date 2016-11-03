package com.emiliano;

import java.util.LinkedList;
import java.util.List;

import com.emiliano.fmframework.core.Configuration;
import com.emiliano.fmframework.core.Feature;
import com.emiliano.fmframework.core.FeatureModel;
import com.emiliano.fmframework.core.FeatureModelImpl;
import com.emiliano.fmframework.core.constraints.crossTreeConstraints.Exclude;
import com.emiliano.fmframework.core.constraints.treeConstraints.AlternativeGroup;
import com.emiliano.fmframework.core.constraints.treeConstraints.MandatoryFeature;
import com.emiliano.fmframework.core.constraints.treeConstraints.OptionalFeature;
import com.emiliano.fmframework.core.constraints.treeConstraints.TreeConstraint;
import com.emiliano.fmframework.operations.ConfOperations;
import com.emiliano.fmframework.optimization.csa.BacktrackingCSA;

public class Main2 {
	public static void main(String[] args) {
		
		FeatureModel model=new FeatureModelImpl();
		model.addFeature(new Feature("root"));
		//classes
		model.addFeature(new Feature("Diagnoser"));
		model.addFeature(new Feature("Region"));
		model.addFeature(new Feature("Vehicle"));
		model.addFeature(new Feature("CarRecord"));
		model.addFeature(new Feature("RoadCamera"));
		//attach classes
		model.addTreeConstraint(new MandatoryFeature("root","Diagnoser"));
		model.addTreeConstraint(new MandatoryFeature("root","Region"));
		model.addTreeConstraint(new MandatoryFeature("root","Vehicle"));
		model.addTreeConstraint(new MandatoryFeature("root","CarRecord"));
		model.addTreeConstraint(new OptionalFeature("root","RoadCamera"));
		//add functions
		model.addFeature(new Feature("DiagnoserRVehicle_LinkRegion"));
		model.addFeature(new Feature("diagPerms2_LinkRegion"));
		model.addFeature(new Feature("Diagnoser_LinkRegion"));
		model.addFeature(new Feature("vehicleAttachementIsModifiedSince"));
		model.addFeature(new Feature("Vehicle_LinkRegion"));
		model.addFeature(new Feature("DiagCarPerms_Modify"));
		model.addFeature(new Feature("CArRecordPerms_Read"));
		model.addFeature(new Feature("carRecordPerms_Create"));
		model.addFeature(new Feature("RegionWithoutChangedInfoCons"));
		model.addFeature(new Feature("NotRegionWithChangedInfoCons"));
		model.addFeature(new Feature("NotRegionWithoutChangedInfoCons"));
		model.addFeature(new Feature("VCPerms_read"));
		model.addFeature(new Feature("VCPerms_followMe"));

		model.addTreeConstraint(new OptionalFeature("Diagnoser","DiagnoserRVehicle_LinkRegion"));
		model.addTreeConstraint(new OptionalFeature("Diagnoser","diagPerms2_LinkRegion"));
		model.addTreeConstraint(new OptionalFeature("Diagnoser","Diagnoser_LinkRegion"));
		
		model.addTreeConstraint(new OptionalFeature("Vehicle","vehicleAttachementIsModifiedSince"));
		model.addTreeConstraint(new OptionalFeature("Vehicle","Vehicle_LinkRegion"));
		
		model.addTreeConstraint(new MandatoryFeature("CarRecord","carRecordPerms_Create"));
		model.addTreeConstraint(new MandatoryFeature("CarRecord","CArRecordPerms_Read"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","DiagCarPerms_Modify"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","RegionWithoutChangedInfoCons"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","NotRegionWithChangedInfoCons"));
		model.addTreeConstraint(new OptionalFeature("CarRecord","NotRegionWithoutChangedInfoCons"));
		
		////////excludes in the same branch
		model.addCrossTreeConstraint(new Exclude("DiagCarPerms_Modify","RegionWithoutChangedInfoCons"));
		model.addCrossTreeConstraint(new Exclude("DiagCarPerms_Modify","NotRegionWithChangedInfoCons"));	
		model.addCrossTreeConstraint(new Exclude("DiagCarPerms_Modify","NotRegionWithoutChangedInfoCons"));
		
		model.addCrossTreeConstraint(new Exclude("RegionWithoutChangedInfoCons","NotRegionWithChangedInfoCons"));	
		model.addCrossTreeConstraint(new Exclude("RegionWithoutChangedInfoCons","NotRegionWithoutChangedInfoCons"));
		
		model.addCrossTreeConstraint(new Exclude("NotRegionWithChangedInfoCons","NotRegionWithoutChangedInfoCons"));
		
		///////////// continure tree constrains
		model.addTreeConstraint(new OptionalFeature("RoadCamera","VCPerms_read"));
		model.addTreeConstraint(new OptionalFeature("RoadCamera","VCPerms_followMe"));
		
		/*
		model.addTreeConstraint(new MandatoryFeature("",""));
		model.addTreeConstraint(new AlternativeGroup("H","D","F"));
		model.addTreeConstraint(new MandatoryFeature("root","B"));
		model.addTreeConstraint(new OptionalFeature("root","C"));
		model.addCrossTreeConstraint(new Exclude("C", "D"));
		model.addTreeConstraint(new OptionalFeature("root","G"));
		*/
		Configuration conf=new Configuration(model);
		ConfOperations.selectFeature(conf, "root");
		System.out.println(conf);
		
//		ConfOperations.selectFeature(conf, "C");
//		System.out.println(conf);
		
		List<Configuration> solutions=new LinkedList();
		BacktrackingCSA alg=new BacktrackingCSA();
		int numOfSolutions=alg.searchAllSolutions(conf,solutions);
		
		System.out.println(numOfSolutions);
		
		for(Configuration conf2:solutions)
			{
			   System.out.println(conf2);
			
			}
		
		System.out.println("Size="+ solutions.size());
		
	}
}
