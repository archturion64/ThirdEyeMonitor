package com.github.archturion64.thirdeye.controllers;

import com.github.archturion64.thirdeye.domains.Cve;
import com.github.archturion64.thirdeye.domains.Device;
import com.github.archturion64.thirdeye.services.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @RequestMapping("/devices")
    public String viewDevicesPage(Model model) {
        return "redirect:/device/list";
    }

    @RequestMapping("/device/list")
    public String showDeviceList(Model model) {
        final List<Device> deviceList = deviceService.listAll();
        model.addAttribute("deviceList", deviceList);
        Cve cve = new Cve();
        model.addAttribute("cve", cve);
        return "device/list_device";
    }

    @RequestMapping("/device/new")
    public String showNewDeviceForm(Model model){
        final Device device = new Device();
        model.addAttribute("device", device);
        return "device/new_device";
    }

    @RequestMapping(value = "/device/save", method = RequestMethod.POST)
    public String saveDevice(@ModelAttribute("device") Device device){
        deviceService.saveDeviceAndVulnerabilities(device);
        return "redirect:/device/list";
    }

    @RequestMapping(value = "/device/edit", method = RequestMethod.POST)
    public String saveEditDevice(@ModelAttribute("device") Device device){
        deviceService.saveDevice(device);
        return "redirect:/device/list";
    }

    @RequestMapping("/device/edit/{id}")
    public ModelAndView editDeviceForm(@PathVariable(name="id") long id){
        ModelAndView mav = new ModelAndView("device/edit_device");

        final Device device = deviceService.get(id);
        mav.addObject("device", device);

        return mav;
    }

    @RequestMapping("/device/delete/{id}")
    public String deleteDevice(@PathVariable(name="id") Long id){
        deviceService.delete(id);
        return "redirect:/device/list";
    }

    @RequestMapping(value = "/device/vulnerability_add/{did}", method=RequestMethod.POST)
    public String addDeviceVulnerability(@PathVariable(name="did") Long did, @ModelAttribute(value = "cve") Cve cve){
        deviceService.addCveToDevice(did, cve.getValue());
        return "redirect:/device/list";
    }

    @RequestMapping("/device/vulnerability_delete/{deviceId}/{vulnerabilityId}")
    public String deleteDeviceVulnerability(@PathVariable(name="deviceId") Long did, @PathVariable(name="vulnerabilityId") Long vid){
        deviceService.removeCveFromDevice(did, vid);
        return "redirect:/device/edit/" + did;
    }
}
