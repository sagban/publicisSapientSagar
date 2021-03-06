package com.sagban.feecalculator;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("manupulator")
public class Manupulator {
	
	private ArrayList<TransactionFormat> al;
	Date d1 ;
	Date d2 ;
	@Autowired
	ExecuteProcessingRules ep;
	@Autowired
	TransactionData td;

	public ArrayList<TransactionFormat> returnElements(){
        SimpleDateFormat sdfo = new SimpleDateFormat("dd-MM-yyyy");
		String file="/Users/sagban/pjp2/feecalculator/client.csv";
		al=td.fromCSV(file);
		ep.processing(al);
		al.stream().forEach(i1->System.out.println(i1.clientid+" "+i1.secid+" "+i1.date+" "+i1.processingfee));
		Comparator<TransactionFormat> c=(a,b)->{
			if(a.clientid.compareTo(b.clientid)<0) {
				return -1;
			}
			else if(a.clientid.compareTo(b.clientid)>0) {
				return 1;
			}
			else {
				int val=0;
				try {
					d1=sdfo.parse(a.date);
					d2=sdfo.parse(b.date);
					
					if(d1.compareTo(d2)>0) {
						val= 1;
					}
					else {
						val= -1;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return val;
			}
		};
		al.sort(c);           
		return al;
	}

}
