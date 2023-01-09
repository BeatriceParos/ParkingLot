package com.parkin.parkinglot.servlets;

import com.parkin.parkinglot.common.UserDto;
import com.parkin.parkinglot.ejb.CarsBean;
import com.parkin.parkinglot.ejb.UserBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_CARS"}))
@WebServlet(name = "AddCar", value = "/AddCar")
public class AddCar extends HttpServlet {
    @Inject
    UserBean userBean;
    @Inject
    CarsBean carsBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users= userBean.findAllUser();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/WEB-INF/pages/addCar.jsp").forward(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String licencePlate= request.getParameter("licence_plate");
        String parkingSpot= request.getParameter("parking_spot");
        Long userId=Long.parseLong(request.getParameter("owner_id"));
        carsBean.createCar(licencePlate, parkingSpot, userId);
        response.sendRedirect(request.getContextPath()+"/Cars");
    }
}
