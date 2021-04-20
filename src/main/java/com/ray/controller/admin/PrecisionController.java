package com.ray.controller.admin;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.ray.common.model.PrecisionInventory;
import com.ray.common.model.PrecisionInventoryCommins;
import com.ray.common.model.PrecisionInventoryDebugging;

import java.util.List;

public class PrecisionController extends Controller {
    public void handle() {
        render("handleInventory.html");
    }

    public void addData() {
        List<Record> list = Db.find("select id from precision_inventory where qrcode = '" + get("qrcode") + "' ");
        Record record = new Record();
        if (list.size() > 0) {
            record.set("code", 0);
            record.set("qrcode", get("qrcode"));
            record.set("message", "qrcode already exist");
            renderJson(record);
        } else {
            PrecisionInventory precisionInventory = getModel(PrecisionInventory.class, "");
            precisionInventory.save();
            record.set("qrcode", get("qrcode"));
            record.set("code", 200);
            record.set("message", "add success");
            renderJson(record);
        }


    }

    public void getTypeList() {
//        PrecisionType precisionType  = getModel(PrecisionType.class,"");
        List<Record> precisionType = Db.find("select *,type as label,type as `value` from precision_type");
        List<Record> precisionTypeCategory = Db.find("select note,note as label,note as `value` from precision_type group by note");
        Record record = new Record();
        record.set("code", 200);
        record.set("precisionTypeCategory", precisionTypeCategory);
        record.set("precisionType", precisionType);
        renderJson(record);
    }

    public void updateData() {
        List<Record> list = Db.find("select * from precision_inventory where qrcode = '" + get("qrcode") + "' ");
        Record record = new Record();
        if (list.size() == 0) {
            record.set("code", 0);
            record.set("qrcode", get("qrcode"));
            record.set("message", "提交失败，二维码不存在");
            renderJson(record);
        } else {

            //是否已经出库
            Boolean isDelivered = list.get(0).get("delivery_date") != null;
            //是否请求出库
            Boolean isReqDeliver = get("delivery_person") != null;

            //是否返修完成
            Boolean isRepaired = list.get(0).get("repair_finish_date") != null;
            //是否请求返修完成
            Boolean isReqRepair = get("repair_result") != null;

            if (isRepaired && isReqRepair) {
                record.set("code", 2);
                record.set("qrcode", get("qrcode"));
                record.set("message", "提交失败，已经返修完成");

            } else if (!isDelivered && isReqRepair) {
                record.set("code", 3);
                record.set("qrcode", get("qrcode"));
                record.set("message", "提交失败，请先提交出库再提交返修结果");

            } else if (isDelivered && isReqDeliver) {
                record.set("code", 1);
                record.set("qrcode", get("qrcode"));
                record.set("message", "提交失败，已经出库");

            } else {


                PrecisionInventory precisionInventory = getModel(PrecisionInventory.class, "");
                int id = list.get(0).getInt("id");  //要更新model必须传id，否则报错
                precisionInventory.set("id", id);
                precisionInventory.update();
                record.set("code", 200);
                record.set("qrcode", get("qrcode"));
                record.set("message", "add success");

            }
        }
        renderJson(record);
    }


    /*
     *   commins 库存管理系列方法
     * */
    public void handleCommins() {
        render("handleInventoryCommins.html");
    }

    public void addDataCommins() {
        List<Record> list = Db.find("select * from precision_inventory_commins where qrcode = '" + get("qrcode") + "'  ");
        Record record = new Record();

        if (list.size() > 0) {
            record.set("code", 0);
            record.set("qrcode", get("qrcode"));
            record.set("message", "qrcode already exist");
            renderJson(record);
        } else {
            PrecisionInventoryCommins precisionInventory = getModel(PrecisionInventoryCommins.class, "");
            precisionInventory.save();
            record.set("qrcode", get("qrcode"));
            record.set("code", 200);
            record.set("message", "add success");
            renderJson(record);
        }


    }

    public void getTypeListCommins() {
        List<Record> precisionType = Db.find("select *,type as label,type as `value` from precision_type_commins");
        List<Record> precisionTypeCategory = Db.find("select panSize,note,note as label,note as `value` from precision_type_commins group by note");
        Record record = new Record();
        record.set("code", 200);
        record.set("precisionTypeCategory", precisionTypeCategory);
        record.set("precisionType", precisionType);
        renderJson(record);
    }

    public void updateDataCommins() {
        List<Record> list = Db.find("select * from precision_inventory_commins where qrcode = '" + get("qrcode") + "' ");
        Record record = new Record();
        if (list.size() == 0) {
            record.set("code", 0);
            record.set("qrcode", get("qrcode"));
            record.set("message", "提交失败，二维码不存在");
            renderJson(record);
        } else {

            //是否已经出库
            Boolean isDelivered = list.get(0).get("delivery_date") != null;
            //是否请求出库
            Boolean isReqDeliver = get("delivery_person") != null;

            //是否返修完成
            Boolean isRepaired = list.get(0).get("repair_finish_date") != null;
            //是否请求返修完成
            Boolean isReqRepair = get("repair_finish_date") != null;

            if (isRepaired && isReqRepair) {
                record.set("code", 2);
                record.set("qrcode", get("qrcode"));
                record.set("message", "提交失败，已经返修完成");

            } else if (!isDelivered && isReqRepair) {
                record.set("code", 3);
                record.set("qrcode", get("qrcode"));
                record.set("message", "提交失败，请先提交出库再提交返修结果");

            } else if (isDelivered && isReqDeliver) {
                record.set("code", 1);
                record.set("qrcode", get("qrcode"));
                record.set("message", "提交失败，已经出库");

            } else {
                PrecisionInventoryCommins precisionInventory = getModel(PrecisionInventoryCommins.class, "");
                int id = list.get(0).getInt("id");  //要更新model必须传id，否则报错
                precisionInventory.set("id", id);
                if (!isReqRepair && get("delivery_qty") == null) {
                    int delivery_qty = precisionInventory.findFirst("select * from precision_inventory_commins where qrcode = '" + get("qrcode") + "'").getInt("entry_qty");
                    precisionInventory.set("delivery_qty", delivery_qty);
                }
                if (isReqRepair && get("repair_qualified_qty") == null) {
                    int delivery_qty = precisionInventory.findFirst("select * from precision_inventory_commins where qrcode = '" + get("qrcode") + "'").getInt("delivery_qty");
                    precisionInventory.set("repair_qualified_qty", delivery_qty);
                }

                precisionInventory.update();

                record.set("code", 200);
                record.set("qrcode", get("qrcode"));
                record.set("message", "add success");

            }
        }
        renderJson(record);
    }

    /*
     *   调试件系列方法
     * */
    public void handleDebugging() {
        render("handleInventoryDebugging.html");
    }

    public void addDataDebugging() {
        List<Record> list = Db.find("select id from precision_inventory_debugging where qrcode = '" + get("qrcode") + "' and entry_operation = '" + get("entry_operation") + "' ");
        Record record = new Record();
        String str = get("qrcode").substring(0, 12);
        List<Record> precisionTypeDebuggings = Db.find(
                "select * from precision_type_debugging where part_no = '" + str + "'  ");

        if (list.size() > 0) {
            record.set("code", 0);
            record.set("qrcode", get("qrcode"));
            record.set("message", "添加失败，二维码已经存在");
            renderJson(record);
        } else {
            if (precisionTypeDebuggings.size() == 0) {
                record.set("code", 1);
                record.set("qrcode", get("qrcode"));
                record.set("message", "添加失败，未找到产品名称");
                renderJson(record);
            } else {
                PrecisionInventoryDebugging precisionInventory = getModel(PrecisionInventoryDebugging.class, "");
                precisionInventory.set("part_name", precisionTypeDebuggings.get(0).get("part_name"));
                precisionInventory.save();
                record.set("qrcode", get("qrcode"));
                record.set("code", 200);
                record.set("message", "add success");
                renderJson(record);
            }


        }
    }

    public void getTypeListDebugging() {
        List<Record> precisionType = Db.find("select part_name,part_no,operations as label,operations as `value` from precision_type_debugging");
        List<Record> precisionTypeCategory = Db.find("select part_name,part_no,operations as label,operations as `value` from precision_type_debugging group by part_no");
        Record record = new Record();
        record.set("code", 200);
        record.set("precisionTypeCategory", precisionTypeCategory);
        record.set("precisionType", precisionType);
        renderJson(record);
    }

    public void updateDataDebugging() {
        List<Record> list = Db.find("select * from precision_inventory_debugging where qrcode = '" + get("qrcode") +
                "' and entry_operation = '" + get("delivery_operation") + "'");
        Record record = new Record();
        if (list.size() == 0) {
            record.set("code", 0);
            record.set("qrcode", get("qrcode"));
            record.set("message", "出库失败，所选的二维码或工序未入库");
            renderJson(record);
        } else {
            //请求的工序是否已经出库
            boolean isDelivered = (list.get(0).get("delivery_operation") != null);
            if (!isDelivered) {
                PrecisionInventoryDebugging precisionInventory = getModel(PrecisionInventoryDebugging.class, "");
                int id = list.get(0).getInt("id");  //要更新model必须传id，否则报错
                precisionInventory.set("id", id);
                precisionInventory.update();
                record.set("code", 200);
                record.set("qrcode", get("qrcode"));
                record.set("message", "add success");
            } else {
                record.set("code", 1);
                record.set("qrcode", get("qrcode"));
                record.set("message", "提交失败，已经出库");
            }

        }
        renderJson(record);
    }
}
