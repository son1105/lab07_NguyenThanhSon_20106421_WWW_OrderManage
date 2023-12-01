# lab07_NguyenThanhSon_20106421_WWW_OrderManage
project about mange product, order,...

-  Xem danh sách sản phẩm, thêm vào giỏ hàng, thanh toán,...:
  +  Để xem danh sách sản phẩm, vào trang http://localhost:8080/products/?size=10&page=1
    ![Screenshot (33)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/adbabcf7-b9cf-4700-8bf7-71dbeb3470f1)
  +  Thêm vào giỏ hàng: click vào button "Add" trong cột "Add To Cart" để thêm sản phẩm vào giỏ hàng. Click 1 lần sẽ tự động thêm só lượng 1,
    click nhiều lần sẽ tự động tăng số lượng nếu sản phẩm đã tồn tại trong giỏ hàng.
  +  Xem giỏ hàng: click vào button "Cart" để đến trang giỏ hàng.
    ![Screenshot (34)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/8bcb734a-af50-4596-8c47-a736c2e8704c)
  + Trong trang giỏ hàng có các chức năng sau:
      * Quay lại trang sản phẩm: click vào button "Back".
      * Xoá sản phẩm trong giỏ hàng: click vào button "Delete" bên cạnh sản phẩm.
      * Thanh toán: click vào button "CheckOut". Sau đó, một hoá đơn cùng với các chi tiết hoá đơn sẽ tự động tạo và lưu xuống cơ sở dữ liệu,
        sau đó chuyển trang đến trang Order để xem order vừa mới được tạo. Đồng thời giỏ hàng sẽ bị xoá hết sản phẩm.
- Trang Order:
  +  Để xem danh sách các order, vào trang sau: http://localhost:8080/orders/
    ![Screenshot (36)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/cf4b4aaf-4d0f-43c2-8c21-ed687eeca7a6)
  + Xem thông tin của order:
      * Xem thông tin của nhân viên đã lập order đó: click vào employeeId trong ô của order muốn xem, sau đó hệ thống sẽ tự động chuyển đến trang hiển thị nhân viên đã chọn.
        ![Screenshot (36)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/e5dde1d1-5c3d-4eef-9ede-cac295bd1a6f)

      * Xem thông tin khách hàng đã order: click vào customerId trong ô của order muốn xem, sau đó hệ thống sẽ tự động chuyển đến trang hiển thị khách hàng đã chọn.
        ![Screenshot (38)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/4fa0fb02-f2e6-4797-9643-fd55a5924f5b)

      * Xem order detail của order: click vào button "View" trong cột "View Detail" của order muốn xem.
        ![Screenshot (40)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/06455bda-bc7b-4480-9f45-ab34372c97f1)

      * Filter: lọc order theo nhân viên hoặc theo ngày: click vào button "Filter" bên góc trái màn hình để chuyển đến trang filter.
        ![Screenshot (41)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/b0aa53d0-a6bd-4aaa-a84f-a98279d27c95)

        Chọn mã nhân viên muốn xem(nếu không chọn hệ thống sẽ chỉ lọc theo ngày)
        Chọn ngày lập order(nếu không chọn "To Date" hệ thống sẽ lọc chính xác các order được lập trong ngày "From Date",
        ngược lại hệ thống sẽ lọc các order được lập trong khoảng thời gian từ "From Date" đến "To Date")
        Sau khi chọn xong nhấn vào button "Filter" để xem danh sách các order đã lọc.
        ![Screenshot (42)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/44412d94-5f8b-4be6-94b8-956f55aacaed)
- Trang admin cho phép thao tác CRUD với các đối tượng:
  +  Truy cập vào trang web: http://localhost:8080/products/admin
    ![Screenshot (43)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/e49c26e4-f278-42e1-bde4-64c420348f0d)
  + Thêm một sản phẩm mới: click vào button "Insert" để chuyển đến trang insert
    ![Screenshot (45)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/3127c6a4-fb18-489b-a97a-6def731c510e)
  + Điền thông tin sản phẩm và click vào button "Insert" để thêm, hệ thống sẽ chuyển đến trang sản phẩm, kéo xuống cuối để coi sản phẩm mới thêm:
    ![Screenshot (46)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/f657394a-2c4f-47a0-9e7f-2c2609ddfac2)
  + Sửa thông tin sản phẩm: click vào button "Update" bên cạnh sản phẩm muốn sửa để chuyển sang trang update
    ![Screenshot (47)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/fa4307c5-8ca6-42a5-9737-34b44a2a2ce7)
  + Sửa lại thông tin và nhấn "Update" để thực hiện sửa thông tin
    ![Screenshot (48)](https://github.com/son1105/lab07_NguyenThanhSon_20106421_WWW_OrderManage/assets/115455297/3070b43a-8916-4e2e-aab1-684f3585164a)
  + Xoá sản phẩm: click vào nút "Delete" bên cạnh sản phẩm muốn xoá, hệ thống sẽ thực hiện update status sản phẩm thành TERMINATED(ngừng bán)




        

