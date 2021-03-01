package com.tandm.abadeliverydriver.main.api;

import com.tandm.abadeliverydriver.main.giaohang.model.ImageGiaoHang;
import com.tandm.abadeliverydriver.main.giaohang.model.StateStop;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.distributionnormswarning.model.DistributionNormsWarning;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historydriverdelivery.model.HistoryDriverDelivery;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.model.HisTotalCartonAndTray;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.model.HistoryStopDriverDelivery;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.model.HistoryStopDriverDelivery2;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historytraydelivery.HistoryTrayDeliveryNotesChild;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historytraydelivery.HistoryTrayDeliveryNotesParent;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypayslip.model.PayslipList;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model.ResultUpdateComment;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model.SalaryDetailList;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model.SalaryList;
import com.tandm.abadeliverydriver.main.home.fragmentHistory.Customer;
import com.tandm.abadeliverydriver.main.home.fragmentHistory.HistoryDelivery;
import com.tandm.abadeliverydriver.main.home.fragmentdieudong.model.DieuDong;
import com.tandm.abadeliverydriver.main.home.fragmentfee.Fee;
import com.tandm.abadeliverydriver.main.home.fragmentfee.ImageFee;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.APRExpensesParent;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.AttachExpenses;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ExpensesAmount;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.FeeType;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.Message;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ResultImage;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ShipmentAdvancePayment;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.feedbackbox.model.FeedbackBox;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model.DetailOrderPayment;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model.OrderPayment;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model.ShipmentOrderPayment;
import com.tandm.abadeliverydriver.main.home.fragmentgiaobu.model.GiaoBu;
import com.tandm.abadeliverydriver.main.home.fragmentgiaobu.model.GiaoBuDetail;
import com.tandm.abadeliverydriver.main.home.fragmenthistorybiker.model.PayslipBiker;
import com.tandm.abadeliverydriver.main.home.fragmenthome.BikerCustomer;
import com.tandm.abadeliverydriver.main.home.fragmenthome.CustomerCode;
import com.tandm.abadeliverydriver.main.home.fragmenthome.Store;
import com.tandm.abadeliverydriver.main.home.fragmenthome.Store2;
import com.tandm.abadeliverydriver.main.home.fragmenthome.StoreATM;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.ADCADShipment;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.CountShipment;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.LocationGeofencing;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.ResultAA;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.Shipment;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.ShipmentBikerForDriver;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.StoreDriver;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.StoreLocation;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.Comment;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.Dock;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.EDI;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.OrderType;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.TimeSlot;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.VehicleType;
import com.tandm.abadeliverydriver.main.home.fragmentinfo.model.UserRating;
import com.tandm.abadeliverydriver.main.home.fragmentop.model.NumberExpensesWaiting;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.model.LateLicenses;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.model.LocationATM;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.model.LocationATMID;
import com.tandm.abadeliverydriver.main.login.User;
import com.tandm.abadeliverydriver.main.nhanhang.model.Item;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBill;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemBooking;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemChild;
import com.tandm.abadeliverydriver.main.nhanhang.model.Problem2s;
import com.tandm.abadeliverydriver.main.nhanhang.model.TotalCartonVin;
import com.tandm.abadeliverydriver.main.nhanhang.model.TotalItems;
import com.tandm.abadeliverydriver.main.roomdb.GeofenceAll2;
import com.tandm.abadeliverydriver.main.suco.BaoCao;
import com.tandm.abadeliverydriver.main.suco.ImageReport;
import com.tandm.abadeliverydriver.main.suco.fragment.Problem;
import com.tandm.abadeliverydriver.main.vcm.ABA_TrayBookingHistory;
import com.tandm.abadeliverydriver.main.vcm.Khay;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MyRequests {

    @FormUrlEncoded
    @POST("token")
    Call<User> login(@Field("UserName") String username, @Field("Password") String pass, @Field("Grant_type") String grantType);

    @FormUrlEncoded
    @POST("api/Account/ChangePassword")
    Call<String> changePass(@Field("UserName") String username,
                            @Field("Id") String id,
                            @Field("Grant_type") String grantType,
                            @Field("OldPassword") String oldPass,
                            @Field("NewPassword") String newPass,
                            @Field("ConfirmPassword") String confirmPass,
                            @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/store/getstore/{store_Code}")
    Call<Store> getInfoStore(@Header("Authorization") String token, @Query("store_Code") String maCuaHang);

    @GET("api/problem/gets")
    Call<Problem> getProblemList(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/report/addnew")
    Call<BaoCao> addBaoCao(@Field("ProblemId") String ProblemId,
                           @Field("Store_Code") String Store_Code,
                           @Field("Note") String Note,
                           @Field("KhachHang") String KhachHang,
                           @Field("orderrelease_id") String orderrelease_id,
                           @Field("CreatedBy") String CreatedBy,
                           @Header("Authorization") String auth);

    @Multipart
    @POST("api/attachment/uploadfile/{ReportId}")
    Call<List<ImageReport>> uploadImage(@Query("ReportId") int id,
                                        @Part MultipartBody.Part file,
                                        @Header("Authorization") String auth);

    @GET("api/PhieuGiaoHang/TotalItem/{store_code}/{deliveryDate}/{khachHang}/{orderrelease_id}")
    Call<TotalItems> getTotalPGH(@Query("store_code") String maCuaHang,
                                 @Query("deliveryDate") String date,
                                 @Query("khachHang") String khachHang,
                                 @Query("orderrelease_id") String orderrelease_id,
                                 @Header("Authorization") String auth);


    @GET("api/PhieuGiaoHang/getlist/{store_code}/{deliveryDate}/{khachHang}/{orderrelease_id}")
    Call<Item> getItemsPGH(@Query("store_code") String maCuaHang, @Query("deliveryDate") String date, @Query("khachHang") String khachHang, @Query("orderrelease_id") String orderrelease_id, @Header("Authorization") String auth);

    @GET("api/PhieuGiaoHang/getlist2/{store_code}/{deliveryDate}/{khachHang}/{orderrelease_id}")
    Call<Item> getItemsPGHICE(@Query("store_code") String maCuaHang, @Query("deliveryDate") String date, @Query("khachHang") String khachHang, @Query("orderrelease_id") String orderrelease_id, @Header("Authorization") String auth);

    @GET("api/problem2/gets")
    Call<Problem2s> getProblem2sList(@Header("Authorization") String auth);

    @PUT("api/PhieuGiaoHang/InsertPGH")
    Call<ItemBill> insertBill(@Header("Authorization") String auth, @Body List<ABA_TrayBookingHistory> item2);

    @PUT("api/PhieuGiaoHang/UpdatePGH")
    Call<ItemBill> updateBooking(@Header("Authorization") String auth, @Body List<ItemBooking> item2);

    @PUT("api/PhieuGiaoHang/UpdateTray")
    Call<ItemBill> updateTrayHistory(@Header("Authorization") String auth,
                                     @Body List<HistoryTrayDeliveryNotesChild> item2);

//    @PUT("api/PhieuGiaoHang/InsertPGH2")
//    Call<ItemBill> insertBill2(@Header("Authorization") String auth, @Body List<Item2> item2);

    //Biker
    @FormUrlEncoded
    @POST("api/TotalDeliveryTrip")
    Call<List<Store2>> getListTrips(@Header("Authorization") String auth, @Field("MaNhanVien") String MaNhanVien, @Field("Delivery_Date") String Delivery_Date);

    @FormUrlEncoded
    @POST("api/CheckCustomerCode")
    Call<CustomerCode> getCustomerCode(@Header("Authorization") String auth, @Field("Username") String Username, @Field("Delivery_Date") String Delivery_Date);

    //Hủy
    @FormUrlEncoded
    @POST("api/UpdateTokenNotiUser")
    Call<String> updateNotiTokenUser(@Header("Authorization") String auth,
                                     @Field("UserName") String UserName,
                                     @Field("TokenNoti") String TokenNoti);

    @FormUrlEncoded
    @POST("api/UpdateTokenNotiUser2")
    Call<String> updateNotiTokenUser2(@Header("Authorization") String auth, @Field("UserName") String UserName, @Field("TokenNoti") String TokenNoti, @Field("DeviceName") String DeviceName, @Field("VersionCode") float VersionCode);

    @FormUrlEncoded
    @POST("api/HistoryDelivery")
    Call<List<HistoryDelivery>> getHistoryDelivery(@Header("Authorization") String auth, @Field("DateFrom") String DateFrom, @Field("DateTo") String DateTo, @Field("UserName") String UserName, @Field("KhachHang") String KhachHang);

    @POST("api/GetCustomer")
    Call<List<Customer>> getCustomer(@Header("Authorization") String auth);

    @POST("api/loaiphi")
    Call<List<Fee>> getFee(@Header("Authorization") String auth);

    @Multipart
    @POST("api/attachment2/uploadfile/{Driver_User}/{Delivery_Date}/{ID_LoaiPhi}/{SoTien}/{GhiChu}")
    Call<List<ImageFee>> uploadImageFee(@Query("Driver_User") String Driver_User,
                                        @Query("Delivery_Date") String Delivery_Date,
                                        @Query("ID_LoaiPhi") String ID_LoaiPhi,
                                        @Query("SoTien") String SoTien,
                                        @Query("GhiChu") String GhiChu,
                                        @Part MultipartBody.Part file,
                                        @Header("Authorization") String auth);


    @Multipart
    @POST("api/attachment3/uploadfile/{Store_Code}/{Delivery_Date}/{NguoiTao}/{KhachHang}/{Note}/{Type}")
    Call<List<ImageGiaoHang>> uploadImageGiaoHang(@Query("Store_Code") String Store_Code,
                                                  @Query("Delivery_Date") String Delivery_Date,
                                                  @Query("NguoiTao") String NguoiTao,
                                                  @Query("KhachHang") String KhachHang,
                                                  @Query("Note") String Note,
                                                  @Query("Type") String Type,
                                                  @Part MultipartBody.Part file,
                                                  @Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("api/GetShipment")
    Call<List<Shipment>> getShipment(@Header("Authorization") String auth,
                                     @Field("DRIVER_ID") String DRIVER_ID,
                                     @Field("FromDate") String FromDate,
                                     @Field("ToDate") String ToDate,
                                     @Field("STATUS") String STATUS);

    @FormUrlEncoded
    @POST("api/GetShipment2")
    Call<List<Shipment>> getShipment2(@Header("Authorization") String auth, @Field("DRIVER_ID") String DRIVER_ID, @Field("FromDate") String FromDate, @Field("ToDate") String ToDate);


    @FormUrlEncoded
    @POST("api/DeniedTrip")
    Call<Integer> addDeniedTrip(@Header("Authorization") String auth,
                                @Field("ATMShipmentID") String ATMShipmentID,
                                @Field("TripDate") String TripDate,
                                @Field("RouteNo") String RouteNo,
                                @Field("DriverGID") String DriverGID,
                                @Field("DriverName") String DriverName,
                                @Field("Reason") String Reason);

    @FormUrlEncoded
    @POST("api/AcceptAndAssign")
    Call<ResultAA> updateStateShipment(@Header("Authorization") String auth,
                                       @Field("Atm_Shipment_id") String Atm_Shipment_id,
                                       @Field("Status") String Status,
                                       @Field("Driver_GID") String Driver_GID);

    @FormUrlEncoded
    @POST("api/CurrentShipment")
    Call<ResultAA> updateStateCurrenShipment(@Header("Authorization") String auth,
                                             @Field("Atm_Shipment_id") String Atm_Shipment_id,
                                             @Field("Driver_GID") String Driver_GID);

    @FormUrlEncoded
    @POST("api/GetStopShipment")
    Call<List<StoreDriver>> getPickShipment(@Header("Authorization") String auth,
                                            @Field("Atm_Shipment_id") String Atm_Shipment_id);

    @FormUrlEncoded
    @POST("api/GetStopShipment2")
    Call<List<StoreDriver>> getStopShipment(@Header("Authorization") String auth,
                                            @Field("MaNhanVien") String MaNhanVien,
                                            @Field("ATM_Shipment_Id") String ATM_Shipment_Id,
                                            @Field("Delivery_Date") String Delivery_Date);

    @FormUrlEncoded
    @POST("api/StateStopDriver")
    Call<StateStop> updateStateStop(@Header("Authorization") String auth,
                                    @Field("Store_Code") String Store_Code,
                                    @Field("Delivery_Date") String Delivery_Date,
                                    @Field("KhachHang") String KhachHang,
                                    @Field("Atm_Shipment_Id") String Atm_Shipment_Id,
                                    @Field("Deficient") int Deficient,
                                    @Field("Enough") boolean Enough,
                                    @Field("Broken") int Broken,
                                    @Field("Residual") int Residual,
                                    @Field("Bad_Temperature") int Bad_Temperature,
                                    @Field("Real_Num_Delivered") int Real_Num_Delivered,
                                    @Field("TotalWeight") String TotalWeight,
                                    @Field("LatApp") String LatApp,
                                    @Field("LngApp") String LngApp,
                                    @Field("orderrelease_id") String orderrelease_id,
                                    @Field("DeliveredBy") String DeliveredBy,
                                    @Field("TotalCartonMasan") int TotalCartonMasan,
                                    @Field("TrayFromNVGN") int TrayFromNVGN
    );

    @FormUrlEncoded
    @POST("api/CountShipment")
    Call<CountShipment> countShipment(@Header("Authorization") String auth, @Field("DRIVER_GID") String DRIVER_GID);

    @FormUrlEncoded
    @POST("api/HistoryDeliveryDriver")
    Call<List<HistoryDriverDelivery>> getHistoryDriverDelivery(@Header("Authorization") String auth, @Field("FromDate") String FromDate, @Field("ToDate") String ToDate, @Field("MaNhanVien") String MaNhanVien, @Field("CustomerCode") String CustomerCode);


    @FormUrlEncoded
    @POST("api/HistoryDeliveryDriverNewVer")
    Call<List<HistoryDriverDelivery>> getHistoryDriverDeliveryNewVer(@Header("Authorization") String auth,
                                                                     @Field("month") int FromDate,
                                                                     @Field("year") int ToDate,
                                                                     @Field("MaNhanVien") String MaNhanVien);


    @FormUrlEncoded
    @POST("api/HistoryStopDeliveryDriver")
    Call<List<HistoryStopDriverDelivery>> getHistoryStopDriverDelivery(@Header("Authorization") String auth, @Field("ATM_SHIPMENT_ID") String ATM_SHIPMENT_ID);

    @FormUrlEncoded
    @POST("api/HistoryStopDeliveryDriver")
    Call<List<HistoryStopDriverDelivery2>> getHistoryStopDriverDelivery2(@Header("Authorization") String auth,
                                                                         @Field("ATM_SHIPMENT_ID") String ATM_SHIPMENT_ID);

    @FormUrlEncoded
    @POST("api/HistoryStopBiker")
    Call<List<HistoryStopDriverDelivery2>> getHistoryStopBikerDelivery2(@Header("Authorization") String auth
            , @Field("Username") String Username
            , @Field("DeliveryDate") String DeliveryDate
            , @Field("Customer") String Customer
    );


    @FormUrlEncoded
    @POST("api/GetShipmentId")
    Call<List<LocationATMID>> getLocationATMID(@Header("Authorization") String auth,
                                               @Field("MaNhanVien") String MaNhanVien);

    @FormUrlEncoded
    @POST("api/GetStopVouchers")
    Call<List<LocationATM>> getLocationATM(@Header("Authorization") String auth, @Field("ATM_SHIPMENT_ID") String ATM_SHIPMENT_ID);

    @FormUrlEncoded
    @POST("api/UpdateArrivedTime")
    Call<Integer> updateArrivedTime(@Header("Authorization") String auth,
                                    @Field("ATM_SHIPMENT_ID") String ATM_SHIPMENT_ID,
                                    @Field("StoreCode") String StoreCode, @Field("LAT") String LAT,
                                    @Field("LNG") String LNG,
                                    @Field("orderrelease_id") String orderrelease_id);

    @FormUrlEncoded
    @POST("api/UpdateStateHubDriver")
    Call<String> UpdateStateHubDriver(@Header("Authorization") String auth,
                                      @Field("Store_Code") String Store_Code,
                                      @Field("Delivery_Date") String Delivery_Date,
                                      @Field("KhachHang") String KhachHang,
                                      @Field("Username") String Username,
                                      @Field("Lat") String Lat, @Field("Lng") String Lng,
                                      @Field("CHNhanDum") String CHNhanDum,
                                      @Field("LyDoNhanDum") String LyDoNhanDum,
                                      @Field("Atm_Shipment_Id") String Atm_Shipment_Id,
                                      @Field("orderrelease_id") String orderrelease_id);

    @FormUrlEncoded
    @POST("api/GetLenhDieuDong")
    Call<List<DieuDong>> GetDieuDong(@Header("Authorization") String auth,
                                     @Field("DRIVER_GID") String DRIVER_GID);

    @GET("api/PhieuGiaoHang/getsalary/{year}/{month}/{DRIVER_ID}")
    Call<SalaryList> getSalary(@Header("Authorization") String auth,
                               @Query("year") String fromDate,
                               @Query("month") String toDate,
                               @Query("DRIVER_ID") String DRIVER_ID);


    @GET("api/PhieuGiaoHang/getsalarydetail/{DRIVER_ID}")
    Call<SalaryDetailList> getSalaryDetail(@Header("Authorization") String auth,
                                           @Query("DRIVER_ID") String DRIVER_ID);


    @GET("api/PhieuGiaoHang/getpayslip/{MaNhanVien}")
    Call<PayslipList> getPayslip(@Header("Authorization") String auth,
                                 @Query("MaNhanVien") String MaNhanVien);

    @GET("api/PhieuGiaoHang/updatecomment/{comment}/{shipmentID}")
    Call<ResultUpdateComment> updateComment(@Header("Authorization") String auth,
                                            @Query("comment") String comment,
                                            @Query("shipmentID") String shipmentID);

    //----------------BHX---------------------

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/store/getstore2/{store_Code}")
    Call<Store> getInfoStore2(@Header("Authorization") String token, @Query("store_Code") String maCuaHang);

    //Bỏ
    @GET("api/PhieuGiaoHang/TotalItem2/{store_code}/{deliveryDate}/{khachHang}/{orderrelease_id}")
    Call<TotalItems> getTotalPGH2(@Query("store_code") String maCuaHang, @Query("deliveryDate") String date, @Query("khachHang") String khachHang, @Query("orderrelease_id") String orderrelease_id, @Header("Authorization") String auth);

    //Bỏ
    @GET("api/PhieuGiaoHang/getlist2/{store_code}/{deliveryDate}/{khachHang}/{orderrelease_id}")
    Call<Item> getItemsPGH2(@Query("store_code") String maCuaHang, @Query("deliveryDate") String date, @Query("khachHang") String khachHang, @Query("orderrelease_id") String orderrelease_id, @Header("Authorization") String auth);

    //-------------------VCM-----------------------------

    @POST("api/GetSoKhay")
    Call<List<Khay>> getKhay(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("api/UpdateGioRoiKhoVCM")
    Call<String> updateGioRoiKhoVCM(@Header("Authorization") String auth,
                                    @Field("ATM_SHIPMENT_ID") String ATM_SHIPMENT_ID,
                                    @Field("LAT") String LAT,
                                    @Field("LNG") String LNG,
                                    @Field("Status") String Status);


    //-------------------TOKYODELI-----------------------------

//    @GET("api/PhieuGiaoHang/getlist3/{store_code}/{deliveryDate}/{khachHang}/{orderrelease_id}")
//    Call<>

    //------------------------------Biker-------------------------------

    //Bỏ
    @FormUrlEncoded
    @POST("api/CheckAssignedBiker")
    Call<Integer> checkAssignedBiker(@Header("Authorization") String auth, @Field("Store_Code") String Store_Code, @Field("Delivery_Date") String Delivery_Date, @Field("KhachHang") String KhachHang, @Field("Packaged_Item_XID") String Packaged_Item_XID);


//    @FormUrlEncoded
//    @POST("api/GetStoreGiaoBu")
//    Call<List<GiaoBu>> getStoreGiaoBu(@Header("Authorization") String auth, @Field("Delivery_Date") String Delivery_Date, @Field("KhachHang") String KhachHang, @Field("ATM_SHIPMENT_ID") String ATM_SHIPMENT_ID);

    @FormUrlEncoded
    @POST("api/GetStoreGiaoBu")
    Call<List<GiaoBu>> getStoreGiaoBu(@Header("Authorization") String auth,
                                      @Field("Delivery_Date") String Delivery_Date,
                                      @Field("KhachHang") String KhachHang);

    @FormUrlEncoded
    @POST("api/GetItemStoreGiaoBu")
    Call<List<GiaoBuDetail>> getItemStoreGiaoBu(@Header("Authorization") String auth,
                                                @Field("Delivery_Date") String Delivery_Date,
                                                @Field("Store_Code") String Store_Code,
                                                @Field("KhachHang") String KhachHang,
                                                @Field("ATM_SHIPMENT_ID") String ATM_SHIPMENT_ID);

    @FormUrlEncoded
    @POST("api/UpdateGiaoBu")
    Call<String> updateItemStoreGiaoBu(@Header("Authorization") String auth,
                                       @Field("RowId") String RowId,
                                       @Field("GiaoBu") int GiaoBu,
                                       @Field("CreatedBy_GiaoBu") String CreatedBy_GiaoBu);

//    @FormUrlEncoded
//    @POST("api/UpdateStateTripDriver")
//    Call<Integer> UpdateStateTripDriver(@Header("Authorization") String auth,
//                                        @Field("Store_Code") String Store_Code,
//                                        @Field("Delivery_Date") String Delivery_Date,
//                                        @Field("KhachHang") String KhachHang,
//                                        @Field("Username") String Username,
//                                        @Field("Lat") String Lat,
//                                        @Field("Lng") String Lng,
//                                        @Field("CHNhanDum") String CHNhanDum,
//                                        @Field("LyDoNhanDum") String LyDoNhanDum,
//                                        @Field("Atm_Shipment_Id") String Atm_Shipment_Id,
//                                        @Field("orderrelease_id") String orderrelease_id,
//                                        @Field("DeliveryBy") String DeliveryBy);

    @FormUrlEncoded
    @POST("api/UpdateStateTripDriver")
    Call<Integer> UpdateStateTripDriver(@Header("Authorization") String auth,
                                        @Field("Store_Code") String Store_Code,
                                        @Field("Delivery_Date") String Delivery_Date,
                                        @Field("KhachHang") String KhachHang,
                                        @Field("Username") String Username,
                                        @Field("Lat") String Lat,
                                        @Field("Lng") String Lng,
                                        @Field("TotalTray") String TotalTray,
                                        @Field("TotalCarton") String TotalCarton,
                                        @Field("TotalCartonMasan") String TotalCartonMasan,
                                        @Field("ProductRecall") String ProductRecall,
                                        @Field("Atm_Shipment_Id") String Atm_Shipment_Id,
                                        @Field("orderrelease_id") String orderrelease_id,
                                        @Field("DeliveryBy") String DeliveryBy);


    @FormUrlEncoded
    @POST("api/UpdateGeoFencingToi")
    Call<Integer> UpdateGeoFencingToi(@Header("Authorization") String auth,
                                      @Field("Atm_Shipment_Id") String Atm_Shipment_Id,
                                      @Field("orderrelease") String orderrelease,
                                      @Field("LatToi") String LatToi,
                                      @Field("LngToi") String LngToi);

    @FormUrlEncoded
    @POST("api/UpdateGeoFencingRoi")
    Call<Integer> UpdateGeoFencingRoi(@Header("Authorization") String auth,
                                      @Field("Atm_Shipment_Id") String Atm_Shipment_Id,
                                      @Field("orderrelease") String orderrelease,
                                      @Field("LatRoi") String LatRoi,
                                      @Field("LngRoi") String LngRoi);


    @FormUrlEncoded
    @POST("api/trackingDepot")
    Call<Integer> trackingDepot(@Header("Authorization") String auth,
                                @Field("Username") String Username,
                                @Field("Status") String Status,
                                @Field("Lat") String Lat,
                                @Field("Lng") String Lng,
                                @Field("DriverId") String DriverId);


    @FormUrlEncoded
    @POST("api/getHistoryDeliveryNote")
    Call<List<ItemChild>> getHistoryDeliveryNote(@Header("Authorization") String auth,
                                                 @Field("Delivery_Date") String Delivery_Date,
                                                 @Field("Store_Code") String Store_Code,
                                                 @Field("ATM_SHIPMENT_ID") String ATM_SHIPMENT_ID,
                                                 @Field("orderrelease_id") String orderrelease_id,
                                                 @Field("customerCode") String customerCode
    );


    @GET("api/PhieuGiaoHang/getlist/{atmshipmentid}/{orderrelease_id}")
    Call<HistoryTrayDeliveryNotesParent> getHistoryTray(@Query("atmshipmentid") String atmshipmentid,
                                                        @Query("orderrelease_id") String orderrelease_id,
                                                        @Header("Authorization") String auth);


    @PUT("api/PhieuGiaoHang/PutPGH")
    Call<ItemBill> updateBill(@Header("Authorization") String auth,
                              @Body List<ItemBooking> item2);


    @FormUrlEncoded
    @POST("api/UpdateHistoryCarton")
    Call<Integer> updateHistoryCarton(@Header("Authorization") String auth,
                                      @Field("ATM_SHIPMENT_ID") String ATM_SHIPMENT_ID,
                                      @Field("Orderrelease_ID") String Orderrelease_ID,
                                      @Field("Real_num_delivered") int Real_num_delivered,
                                      @Field("Enough") boolean Enough,
                                      @Field("Deficient") int Deficient,
                                      @Field("Broken") int Broken,
                                      @Field("Residual") int Residual,
                                      @Field("Bad_temperature") int Bad_temperature
    );

    @GET("api/LoadLocationGeoFencing")
    Call<List<LocationGeofencing>> getLocationGeofencing(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("api/GetLatLngStop")
    Call<List<StoreLocation>> GetLatLngStop(@Header("Authorization") String auth,
                                            @Field("ATM_Shipment_Id") String ATM_Shipment_Id);

    @FormUrlEncoded
    @POST("api/GetAtmShipmentIDGiaoBu")
    Call<List<LocationATMID>> GetAtmShipmentIDGiaoBu(@Header("Authorization") String auth,
                                                     @Field("DeliveryDate") String DeliveryDate);

    @GET("api/GetAdvanceFee")
    Call<List<FeeType>> getFeeType(@Header("Authorization") String auth);


    @FormUrlEncoded
    @POST("api/GetInfoAdvanceFee")
    Call<List<ShipmentAdvancePayment>> GetShipmentAdvancePayment(@Header("Authorization") String auth,
                                                                 @Field("DriverGID") String DriverGID);

    @FormUrlEncoded
    @POST("api/apr/addnew")
    Call<APRExpensesParent> SendTamUng(@Header("Authorization") String auth,
                                       @Field("ATMShipmentID") String ATMShipmentID,
                                       @Field("OTMShipmentID") String OTMShipmentID,
                                       @Field("PowerUnit") String PowerUnit,
                                       @Field("EmployeeID") String EmployeeID,
                                       @Field("EmployeeName") String EmployeeName,
                                       @Field("Department") String Department,
                                       @Field("Customer") String Customer,
                                       @Field("InvoiceNumber") String InvoiceNumber,
                                       @Field("AdvancePaymentType") String AdvancePaymentType,
                                       @Field("Description") String Description,
                                       @Field("Amount") String Amount,
                                       @Field("City") String City,
                                       @Field("StartTime") String StartTime
    );

    @PUT("api/apr/addnewop")
    Call<List<DetailOrderPayment>> SendThanhToan(@Header("Authorization") String auth,
                                                 @Body List<DetailOrderPayment> item2);

    @PUT("api/PhieuGiaoHang/UpdateGeofence")
    Call<ItemBill> UpdateGeofence(@Header("Authorization") String auth, @Body List<GeofenceAll2> item2);

//    @FormUrlEncoded
//    @POST("api/apr/addnewop")
//    Call<Boolean> SendThanhToan(@Header("Authorization") String auth,
//                                          @Field("ATMShipmentID") String ATMShipmentID,
//                                          @Field("PowerUnit") String PowerUnit,
//                                          @Field("EmployeeID") String EmployeeID,
//                                          @Field("EmployeeName") String EmployeeName,
//                                          @Field("Department") String Department,
//                                          @Field("Customer") String Customer,
//                                          @Field("AdvancePaymentType") String AdvancePaymentType,
//                                          @Field("Description") String Description,
//                                          @Field("Amount") String Amount
//    );


    @Multipart
    @POST("api/attachment5/uploadfile/{ID}/{ATMBuyshipment}/{DocumentType}")
    Call<List<ResultImage>> uploadImageFee2(@Header("Authorization") String auth,
                                            @Query("ID") int ID,
                                            @Query("ATMBuyshipment") String ATMBuyshipment,
                                            @Query("DocumentType") String DocumentType,
                                            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("api/attachment5/uploadfile/{ID}/{ATMBuyshipment}/{DocumentType}")
    Call<List<ResultImage>> uploadImageFee3(@Header("Authorization") String auth,
                                            @Query("ID") int ID,
                                            @Query("ATMBuyshipment") String ATMBuyshipment,
                                            @Query("DocumentType") String DocumentType,
                                            @Part List<MultipartBody.Part> file);


    @Multipart
    @POST("api/attachment5/addfile/{ID}/{ATMBuyshipment}/{DocumentType}")
    Call<List<ResultImage>> addImageFee(@Header("Authorization") String auth,
                                        @Query("ID") int ID,
                                        @Query("ATMBuyshipment") String ATMBuyshipment,
                                        @Query("DocumentType") String DocumentType,
                                        @Part MultipartBody.Part file);

    @Multipart
    @POST("api/attachment5/addfileop/{ID}/{ATMBuyshipment}")
    Call<List<ResultImage>> addImageOP(@Header("Authorization") String auth,
                                       @Query("ID") int ID,
                                       @Query("ATMBuyshipment") String ATMBuyshipment,
                                       @Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST("api/getStopBikerFromDriver")
    Call<List<ShipmentBikerForDriver>> getStopBikerFromDriver(@Header("Authorization") String auth,
                                                              @Field("RouteNo") String RouteNo,
                                                              @Field("CustomerCode") String CustomerCode,
                                                              @Field("StartTime") String StartTime);

    @GET("api/GetBikerCustomer")
    Call<List<BikerCustomer>> GetBikerCustomer(@Header("Authorization") String auth);


    @GET("api/store/getstore3/{DeliveryDate}/{CustomerCode}/{LocationGID}")
    Call<StoreATM> getInfoStoreATM(@Header("Authorization") String auth,
                                   @Query("DeliveryDate") String DeliveryDate,
                                   @Query("CustomerCode") String CustomerCode,
                                   @Query("LocationGID") String LocationGID);


    @GET("api/store/getstore4/{DeliveryDate}/{CustomerCode}/{LocationGID}")
    Call<List<StoreATM>> getInfoStoreATM2(@Header("Authorization") String auth,
                                          @Query("DeliveryDate") String DeliveryDate,
                                          @Query("CustomerCode") String CustomerCode,
                                          @Query("LocationGID") String LocationGID);


    @Headers({"Accept:application/json", "Content-Type:application/x-www-form-urlencoded;"})
    @GET("api/Checkversionapp")
    Call<Float> getVersionChecker();


    @FormUrlEncoded
    @POST("api/GetExpensesAmount")
    Call<List<ExpensesAmount>> GetExpensesAmount(@Header("Authorization") String auth,
                                                 @Field("Month") String Month,
                                                 @Field("Year") String Year,
                                                 @Field("MaNhanVien") String MaNhanVien);

    @FormUrlEncoded
    @POST("api/GetExpensesAttachment")
    Call<List<AttachExpenses>> GetExpensesAttachment(@Header("Authorization") String auth,
                                                     @Field("ID") int ID);


    @GET("api/apr/getop2/{manager}/{department}/{invoiceDate}")
    Call<List<ExpensesAmount>> GetOp2(@Header("Authorization") String auth,
                                      @Query("manager") String manager,
                                      @Query("department") String department,
                                      @Query("invoiceDate") String invoiceDate);


    @GET("api/apr/getop4/{manager}/{department}/{invoiceDate}")
    Call<List<ExpensesAmount>> GetOp4(@Header("Authorization") String auth,
                                      @Query("manager") String manager,
                                      @Query("department") String department,
                                      @Query("invoiceDate") String invoiceDate);

    @GET("api/apr/getkt/{manager}/{department}/{invoiceDate}")
    Call<List<ExpensesAmount>> GetKT(@Header("Authorization") String auth,
                                     @Query("manager") String manager,
                                     @Query("department") String department,
                                     @Query("invoiceDate") String invoiceDate);

    @GET("api/apr/getktmanager/{manager}/{department}/{invoiceDate}")
    Call<List<ExpensesAmount>> GetKTManager(@Header("Authorization") String auth,
                                            @Query("manager") String manager,
                                            @Query("department") String department,
                                            @Query("invoiceDate") String invoiceDate);


    @FormUrlEncoded
    @POST("api/TriggerAdvancedPayment")
    Call<Boolean> TriggerAdvancedPayment(@Header("Authorization") String auth,
                                         @Field("topic") String topic,
                                         @Field("fullName") String fullName);

    @FormUrlEncoded
    @POST("api/apr/removeexpenses")
    Call<Message> RemoveExpenses(@Header("Authorization") String auth,
                                 @Field("ID") int ID,
                                 @Field("AdvancePaymentType") String AdvancePaymentType);

    @FormUrlEncoded
    @POST("api/apr/removeop")
    Call<Message> RemoveOP(@Header("Authorization") String auth,
                           @Field("ID") int ID);

    @FormUrlEncoded
    @POST("api/apr/editexpenses")
    Call<Message> EditExpenses(@Header("Authorization") String auth,
                               @Field("ID") int ID,
                               @Field("Amount") int Amount,
                               @Field("Description") String Description,
                               @Field("AdvancePaymentType") String AdvancePaymentType);

    @FormUrlEncoded
    @POST("api/apr/editop")
    Call<Message> EditOP(@Header("Authorization") String auth,
                         @Field("ID") int ID,
                         @Field("Amount") int Amount,
                         @Field("Description") String Description,
                         @Field("AdvancePaymentType") String AdvancePaymentType
    );


    @FormUrlEncoded
    @POST("api/GetShipmentOrderPayment")
    Call<List<OrderPayment>> GetShipmentOrderPayment(@Header("Authorization") String auth,
                                                     @Field("Month") int Month,
                                                     @Field("Year") int Year,
                                                     @Field("EmployeeID") String MaNhanVien);

    @FormUrlEncoded
    @POST("api/apr/removeexpensesfile")
    Call<Message> DeleteAttachment(@Header("Authorization") String auth,
                                   @Field("ID") int ID,
                                   @Field("AttachName") String AttachName,
                                   @Field("DocumentType") String DocumentType);

    @FormUrlEncoded
    @POST("api/apr/updatestateop")
    Call<Message> UpdateStateOP(@Header("Authorization") String auth,
                                @Field("ID") int ID,
                                @Field("Manager") String Manager,
                                @Field("Remark") String Remark,
                                @Field("UpdatedByManager") String UpdatedByManager
    );

    @FormUrlEncoded
    @POST("api/apr/updatestateop2")
    Call<Message> UpdateStateOP2(@Header("Authorization") String auth,
                                 @Field("ID") int ID,
                                 @Field("Manager") String Manager,
                                 @Field("Remark") String Remark,
                                 @Field("UpdatedByOP") String UpdatedByOP
    );


    @FormUrlEncoded
    @POST("api/apr/updatestatekt")
    Call<Message> UpdateStateKT(@Header("Authorization") String auth,
                                @Field("ID") int ID,
                                @Field("Manager") String Manager,
                                @Field("Remark") String Remark);


    @FormUrlEncoded
    @POST("api/apr/updatestatektmanager")
    Call<Message> UpdateStateKTManager(@Header("Authorization") String auth,
                                       @Field("ID") int ID,
                                       @Field("Manager") String Manager,
                                       @Field("Remark") String Remark);

    @FormUrlEncoded
    @POST("api/GetShipmentIDOP")
    Call<List<ShipmentOrderPayment>> GetShipmentIDOP(@Header("Authorization") String auth,
                                                     @Field("EmployeeID") String EmployeeID);


    @FormUrlEncoded
    @POST("api/GetDetailOP")
    Call<List<DetailOrderPayment>> GetDetailOP(@Header("Authorization") String auth,
                                               @Field("ATMShipmentID") String ATMShipmentID);

    @FormUrlEncoded
    @POST("api/TriggerStateFromOPToUser")
    Call<Boolean> TriggerStateFromOPToUser(@Header("Authorization") String auth,
                                           @Field("topic") String topic,
                                           @Field("fullName") String fullName,
                                           @Field("fullName2") String fullName2,
                                           @Field("state") String state,
                                           @Field("FeeType") String FeeType);

    @FormUrlEncoded
    @POST("api/TriggerStateFromOPToManager")
    Call<Boolean> TriggerStateFromOPToManager(@Header("Authorization") String auth,
                                              @Field("fullName") String fullName,
                                              @Field("fullName2") String fullName2,
                                              @Field("region") String region,
                                              @Field("state") String state,
                                              @Field("FeeType") String FeeType);


    @FormUrlEncoded
    @POST("api/TriggerStateFromKTToKTMa")
    Call<Boolean> TriggerStateFromKTToKTMa(@Header("Authorization") String auth,
                                           @Field("fullName") String fullName,
                                           @Field("fullName2") String fullName2,
                                           @Field("region") String region,
                                           @Field("state") String state,
                                           @Field("FeeType") String FeeType);

    @FormUrlEncoded
    @POST("api/CheckFeeOPWaiting")
    Call<NumberExpensesWaiting> CheckFeeOPWaiting(@Header("Authorization") String auth,
                                                  @Field("Region") String Region,
                                                  @Field("Position") String Position);


    @FormUrlEncoded
    @POST("api/API_DistributionNormsWarning")
    Call<List<DistributionNormsWarning>> getDistributionNormsWarning(@Header("Authorization") String auth,
                                                                     @Field("DriverCode") String DriverCode);


    @FormUrlEncoded
    @POST("api/GetTotalCartonVin")
    Call<TotalCartonVin> GetTotalCartonVin(@Header("Authorization") String auth,
                                           @Field("DeliveryDate") String DeliveryDate,
                                           @Field("CustomerName") String CustomerName,
                                           @Field("ShipToCode") String ShipToCode);


    @FormUrlEncoded
    @POST("api/GetHisTrayAndCarton")
    Call<HisTotalCartonAndTray> GetHisTrayAndCarton(@Header("Authorization") String auth,
                                                    @Field("ATMShipmentID") String ATMShipmentID,
                                                    @Field("ATMOrderReleaseID") String ATMOrderReleaseID,
                                                    @Field("LocationGID") String LocationGID,
                                                    @Field("CustomerCode") String CustomerCode);

    @FormUrlEncoded
    @POST("api/UpdateHisTotalCartonAndTray")
    Call<Integer> UpdateHisTotalCartonAndTray(@Header("Authorization") String auth,
                                              @Field("RealNumDelivered") String RealNumDelivered,
                                              @Field("TotalTray") String TotalTray,
                                              @Field("ProductRecall") String ProductRecall,
                                              @Field("AtmOrderreleaseID") String AtmOrderreleaseID,
                                              @Field("LocationGID") String LocationGID,
                                              @Field("ATMShipmentID") String ATMShipmentID,
                                              @Field("CustomerCode") String CustomerCode
    );

    @FormUrlEncoded
    @POST("api/CountShipmentAssigned")
    Call<CountShipment> countShipmentAssigned(@Header("Authorization") String auth,
                                              @Field("DRIVER_GID") String DRIVER_GID,
                                              @Field("FromDate") String FromDate);

    @FormUrlEncoded
    @POST("api/CountShipmentAccepted")
    Call<CountShipment> countShipmentAccepted(@Header("Authorization") String auth,
                                              @Field("DRIVER_GID") String DRIVER_GID,
                                              @Field("FromDate") String FromDate);

    //-------------------------------- Late Licenses --------------------------
    @FormUrlEncoded
    @POST("api/latelicenses/addnewlatelicenses")
    Call<String> addLateLicenses(@Header("Authorization") String auth, @Field("ATMShipmentID") String ATMShipmentID,
                                 @Field("Customer") String Customer,
                                 @Field("StartTime") String StartTime,
                                 @Field("DriverID") String DriverID,
                                 @Field("DateOfFiling") String DateOfFiling,
                                 @Field("Reason") String Reason,
                                 @Field("FullName") String FullName,
                                 @Field("Region") String Region);


    @GET("api/latelicenses/getlatelicenses/{manhanvien}")
    Call<List<LateLicenses>> GetLateLicensesDriver(@Header("Authorization") String auth,
                                                   @Query("manhanvien") String manhanvien);


    @FormUrlEncoded
    @POST("api/latelicenses/removelatelicenses")
    Call<String> removeLateLicenses(@Header("Authorization") String auth,
                                    @Field("Id") int Id);

    @FormUrlEncoded
    @POST("api/latelicenses/editlatelicenses")
    Call<String> editLateLicenses(@Header("Authorization") String auth, @Field("Id") int Id,
                                  @Field("Reason") String Reason,
                                  @Field("DateOfFiling") String DateOfFiling
    );


    @FormUrlEncoded
    @POST("api/TriggerLatLicensesFromUserToMa")
    Call<Boolean> TriggerLateLicensesUserToMa(@Header("Authorization") String auth,
                                              @Field("fullName2") String fullName2,
                                              @Field("region") String region);


    @GET("api/latelicenses/getlatelicensesop/{status}/{region}")
    Call<List<LateLicenses>> GetLateLicensesOP(@Header("Authorization") String auth,
                                               @Query("status") String status,
                                               @Query("region") String region);

    @FormUrlEncoded
    @POST("api/latelicenses/updatelatelicensesfromop")
    Call<String> updateLateLicensesFromOP(@Header("Authorization") String auth,
                                          @Field("Id") int Id,
                                          @Field("DateOfFiling") String DateOfFiling,
                                          @Field("StatusManager") String StatusManager,
                                          @Field("ManagerApprovedName") String ManagerApprovedName,
                                          @Field("NoteFromManage") String NoteFromManage
    );


    @FormUrlEncoded
    @POST("api/TriggerLateLicensesFromMaToUser")
    Call<Boolean> TriggerLateLicensesFromMaToUser(@Header("Authorization") String auth,
                                                  @Field("topic") String topic,
                                                  @Field("fullName2") String fullName2,
                                                  @Field("state") String state
    );

    //////////////////////////Feedback///////////////////////////
    @GET("api/feedback/getfb/{driverId}")
    Call<List<FeedbackBox>> GetFeedbackBox(@Header("Authorization") String auth,
                                           @Query("driverId") String driverId);

    @FormUrlEncoded
    @POST("api/feedback/addnewfb")
    Call<FeedbackBox> AddFeedback(@Header("Authorization") String auth,
                                  @Field("DriverName") String DriverName,
                                  @Field("DriverID") String DriverID,
                                  @Field("ATMShipmentID") String ATMShipmentID,
                                  @Field("Type") String Type,
                                  @Field("Content") String Content,
                                  @Field("Title") String Title
    );


    @FormUrlEncoded
    @POST("api/feedback/removefb")
    Call<String> RemoveFeedback(@Header("Authorization") String auth,
                                @Field("ID") String ID
    );

    @FormUrlEncoded
    @POST("api/GetShipmentIDFB")
    Call<List<ShipmentOrderPayment>> GetShipmentIDFB(@Header("Authorization") String auth,
                                                     @Field("EmployeeID") String EmployeeID);


    @GET("api/PhieuGiaoHang/getpayslipbiker/{MaNhanVien}")
    Call<List<PayslipBiker>> GetPayslipBiker(@Header("Authorization") String auth,
                                             @Query("MaNhanVien") String MaNhanVien);


    //---------------------------Zalo---------------------------

    @FormUrlEncoded
    @POST("api/ZNSRating")
    Call<List<UserRating>> GetZNSRating(@Header("Authorization") String auth,
                                        @Field("DriverCode") String DriverCode,
                                        @Field("Month") int Month,
                                        @Field("Year") int Year
    );

    //-----------------------------------Chụp hình ADCAD----------------------------------------


    @Multipart
    @POST("api/attachmentshipment/addfile/{ID}")
    Call<ResultImage> addADCADShipmentFile(@Header("Authorization") String auth,
                                           @Query("ID") int ID,
                                           @Part List<MultipartBody.Part> file);

    @FormUrlEncoded
    @POST("api/adcadshipment/addnew")
    Call<ADCADShipment> addADCADShipment(@Header("Authorization") String auth,
                                         @Field("ATMShipmentId") String ATMShipmentId,
                                         @Field("StartTime") String StartTime,
                                         @Field("DriverName") String DriverName,
                                         @Field("DriverID") String DriverID
    );

    //-------------------------------------------------WMS  MyRetrofitWMS-------------------------------------------------------

    @POST("api/EDI_OrderType")
    Call<List<OrderType>> getOrderType();

    @POST("api/TimeSlotList")
    Call<List<TimeSlot>> getTimeSlotList();

    @POST("api/VehicleType")
    Call<List<VehicleType>> getVehicleType();

    @FormUrlEncoded
    @POST("api/DockList")
    Call<List<Dock>> getDockList(@Field("UserName") String Username);

    @FormUrlEncoded
    @POST("api/EDI_OrdersInsert")
    Call<String> insertNhapXuat(@Field("PhoneNumber") String PhoneNumber,
                                @Field("UserName") String UserName,
                                @Field("DeviceNumber") String DeviceNumber,
                                @Field("OrderDate") String OrderDate,
                                @Field("TimeSlotID") int TimeSlotID,
                                @Field("TruckNumber") String TruckNumber,
                                @Field("CustomerReference") String CustomerReference,
                                @Field("TotalQuantity") String TotalQuantity,
                                @Field("TotalWeights") String TotalWeights,
                                @Field("VehicleType") String VehicleType,
                                @Field("DockDoorID") int DockDoorID,
                                @Field("OrderType") int OrderType
    );

    @FormUrlEncoded
    @POST("api/EDI_OrdersView")
    Call<List<EDI>> getEDI(@Field("UserName") String Username,
                           @Field("OrderDate") String OrderDate
    );

    @FormUrlEncoded
    @POST("api/RatingComment")
    Call<List<Comment>> getRatingComment(@Field("OrderNumber") String OrderNumber,
                                         @Field("CommentID") String CommentID,
                                         @Field("CommentBy") String CommentBy,
                                         @Field("Comment") String Comment,
                                         @Field("Flag") String Flag
    );

    @FormUrlEncoded
    @POST("api/RatingInsert")
    Call<String> insertRatingComment(@Field("UserName") String Username,
                                     @Field("OrderNumber") String OrderNumber,
                                     @Field("RatingValue") float RatingValue
    );


    @FormUrlEncoded
    @POST("api/EDI_OrdersUpdateStatus")
    Call<String> updateStatusArrive(@Field("EDI_OrderID") String EDI_OrderID,
                                     @Field("UserName") String UserName,
                                     @Field("DeviceNumber") String DeviceNumber,
                                     @Field("StatusDescription") String StatusDescription
    );

    @FormUrlEncoded
    @POST("api/EDI_OrdersUpdate")
    Call<String> deleteEDI(@Field("EDI_OrderID") String EDI_OrderID,
                                    @Field("UserName") String UserName,
                                    @Field("DeviceNumber") String DeviceNumber,
                                    @Field("OrderDate") String OrderDate,
                                    @Field("TimeSlotID") String TimeSlotID,
                                    @Field("TruckNumber") String TruckNumber,
                                    @Field("CustomerReference") String CustomerReference,
                                    @Field("TotalQuantity") String TotalQuantity,
                                    @Field("TotalWeights") String TotalWeights,
                                    @Field("VehicleType") String VehicleType,
                                    @Field("DockDoorID") String DockDoorID,
                                    @Field("OrderType") String OrderType,
                                    @Field("Flag") String Flag
    );


}
