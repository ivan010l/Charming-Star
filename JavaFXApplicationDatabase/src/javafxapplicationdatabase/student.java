/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxapplicationdatabase;

/**
 *
 * @author Surface Laptop 3
 */
public class student {
    private Integer id;
    private String name;
    private String dept;
    private String age;
    private String email;

    public student(Integer id, String name, String dept, String age, String email) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.age = age;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
    
    
            
       
    
}