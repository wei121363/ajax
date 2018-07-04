package com.example.demo;

import com.example.demo.JSONEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ServiceApi {

    @RequestMapping("/hello")
    public JSONEntity sayHello(){

        JSONEntity entity=new JSONEntity();
        entity.setData("abcd");
        entity.setStatus(0);
        return entity;

    }



    @RequestMapping("/login")
    public JSONEntity login(HttpServletRequest req){


       Object obj= req.getSession().getAttribute("login");
      if(obj==null)
      {
          req.getSession(true).setAttribute("login","true");
      }

        JSONEntity entity=new JSONEntity();
      Map aa=new HashMap();
      aa.put("tocken",req.getSession(false).getId());
        entity.setData(aa);
        entity.setStatus(0);

        return entity;

    }

    @RequestMapping("/logout")
    public JSONEntity logout(HttpServletRequest req){


        HttpSession obj= req.getSession();
        if(obj!=null)
        {
            obj.invalidate();
         }

        JSONEntity entity=new JSONEntity();
        entity.setData("ok");
        entity.setStatus(1);

        return entity;

    }


}
