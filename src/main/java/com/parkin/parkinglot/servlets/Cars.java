package com.parkin.parkinglot.servlets;

import com.parkin.parkinglot.common.CarDto;
import com.parkin.parkinglot.ejb.CarsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Cars", value = "/Cars")
public class Cars extends HttpServlet {

    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CarDto> cars= carsBean.findAllCars();
        request.setAttribute("cars", cars);

        request.setAttribute("activePage", "Cars");
        request.setAttribute("numberOfFreeParkingSpots", 10);
        request.getRequestDispatcher("/WEB-INF/pages/cars.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] car_ids_as_String;
        car_ids_as_String = request.getParameterValues("car_ids");
        if(car_ids_as_String != null){
            List<Long> car_ids = new ArrayList<>();
            for(String car_id_String : car_ids_as_String){
                car_ids.add(Long.parseLong(car_id_String));
            }
            carsBean.deleteCarsByIds(car_ids);
        }
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}
