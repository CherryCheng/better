package top.cherrycheng.better.lamda.myTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ChengRu
 * @date 2021/9/1 11:46
 * @Desc
 */
public class TestListDiff {
    public static void main(String[] args) {
        final ArrayList<String> arrayList = new ArrayList<>();
        List<String> strings =
                Arrays.asList("name", "name", "sex", "age", "mobile", "certType", "certNo", "idCardOcrValidStart",
                        "idCardOcrValidEnd", "marriage", "education", "companyName", "businessType", "monthIncomeRange",
                        "jobOccupation", "companyTel", "companyAddress", "housingProperty", "homeAddress",
                        "contactsFirstName", "contactsFirstRelation", "contactsFirstPhone", "contactsSecondName",
                        "contactsSecondRelation", "contactsSecondPhone", "deviceId", "ipAddress", "deviceType",
                        "macAddress", "deviceHitRisk", "gps", "name", "certNo", "idCardOcrSex", "idCardOcrNation",
                        "idCardOcrBirth", "idCardOcrDomicileAddress", "idCardOcrRegisteredAddress",
                        "idCardOcrValidStart", "idCardOcrValidEnd", "idCardFrontSideImg", "idCardReverseSideImg",
                        "handIDCardFrontSideImg", "handIDCardReverseSideImg", "idCardFrontSideImg",
                        "idCardReverseSideImg", "personFaceImg", "personFaceVideo");
        List<String> dbField =
                Arrays.asList("acctId", "applyDate", "applyFlowNo", "Apply_UseCreditDays_I", "backPhoto", "bizCode",
                        "callBackUrl", "carLoan", "carNumber", "certNo", "certNoAddr", "companyAddress", "companyName",
                        "contactsFirstName", "contactsFirstPhone", "contactsFirstRelation", "contactsSecondName",
                        "contactsSecondPhone", "contactsSecondRelation", "debitCard", "deviceId", "devToken",
                        "education", "financialProducts", "frontPhoto", "guarantor", "guarantorNo", "guarantorPhone",
                        "hko", "homeAddress", "housingLoan", "housingOwnerShip", "housingProperty", "id",
                        "idCardOcrBirth", "idCardOcrDomicileAddress", "idCardOcrNation", "idCardOcrRegisteredAddress",
                        "idCardOcrValidEnd", "idCardOcrValidStart", "ipAddress", "jobOccupation", "lbsAddress",
                        "livingBody", "loanCard", "macAddress", "marriage", "mobile", "monthIncome", "msgTime", "name",
                        "openAccountSec", "owner", "phase", "prevId", "reference_field1", "regTime", "test0", "test1",
                        "test10", "test2", "test3", "test4", "test5", "test7", "test8", "tr", "userId", "user_test01",
                        "vehicleType", "vin", "yearIncome");

        final List<String> accessFiels =
                Arrays.asList("sex", "age", "certType", "businessType", "monthIncomeRange", "companyTel", "deviceType",
                        "deviceHitRisk", "gps", "idCardOcrSex", "idCardFrontSideImg", "idCardReverseSideImg",
                        "handIDCardFrontSideImg", "handIDCardReverseSideImg", "personFaceImg", "personFaceVideo",
                        "certNo", "name", "bizCode", "mobile", "idCardOcrRegisteredAddress", "idCardOcrDomicileAddress",
                        "idCardOcrValidStart", "idCardOcrValidEnd", "ipAddress", "frontPhoto", "backPhoto", "education",
                        "marriage", "homeAddress", "companyName", "companyAddress", "contactsFirstName",
                        "contactsFirstPhone", "contactsSecondName", "contactsSecondPhone", "contactsFirstRelation",
                        "contactsSecondRelation", "housingProperty", "idCardOcrNation", "idCardOcrBirth",
                        "jobOccupation", "applyFlowNo", "macAddress", "deviceId");

        strings.stream().forEach(x -> {
            //                    数据库里比Excel里少的
            //                    if(!dbField.contains(x)){
            //                        arrayList.add(x);
            //                    }

            //                    access_param里少加的
            if (!accessFiels.contains(x)) {
                arrayList.add(x);
            }
        });
        System.out.println(arrayList.stream().distinct().collect(Collectors.joining(",")));
    }
}
