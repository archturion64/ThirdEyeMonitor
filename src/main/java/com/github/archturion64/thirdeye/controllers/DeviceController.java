package com.github.archturion64.thirdeye.controllers;

import com.github.archturion64.thirdeye.domains.Cve;
import com.github.archturion64.thirdeye.domains.Device;
import com.github.archturion64.thirdeye.services.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @GetMapping("/device/list")
    public String viewDeviceList(Principal principal, Model model){
        final List<Device> deviceList = deviceService.listAll(principal);
        model.addAttribute("deviceList", deviceList);
        model.addAttribute("cve", new Cve());
        return "device/list_devices";
    }

    @RequestMapping("/device/new")
    public String showNewDeviceForm(Model model){
        final Device device = new Device();
        model.addAttribute("device", device);
        return "device/new_device";
    }

    @RequestMapping(value = "/device/save", method = RequestMethod.POST)
    public String saveDevice(Principal principal, @ModelAttribute("device") Device device){
        deviceService.add(principal, device);

        return "redirect:/device/list";
    }

    @RequestMapping(value = "/device/edit", method = RequestMethod.POST)
    public String saveEditDevice(Principal principal, @ModelAttribute("device") Device device) throws IllegalAccessException {
        deviceService.edit(principal, device);
        return "redirect:/device/list";
    }

    @RequestMapping("/device/edit/{id}")
    public ModelAndView editDeviceForm(Principal principal, @PathVariable(name="id") long id) throws IllegalAccessException {
        ModelAndView mav = new ModelAndView("device/edit_device");
        final Device device = deviceService.get(principal, id);
        mav.addObject("device", device);

        return mav;
    }

    @RequestMapping("/device/delete/{id}")
    public String deleteDevice(Principal principal, @PathVariable(name="id") Long id) throws IllegalAccessException {
        deviceService.delete(principal, id);

        return "redirect:/device/list";
    }

    @RequestMapping(value = "/device/vulnerability_add/{did}", method=RequestMethod.POST)
    public String addDeviceVulnerability(Principal principal, @PathVariable(name="did") Long did, @ModelAttribute(value = "cve") Cve cve) throws IllegalAccessException {
        deviceService.addCveToDevice(principal, did, cve.getValue());

        return "redirect:/device/list";
    }

    @RequestMapping("/device/vulnerability_delete/{deviceId}/{vulnerabilityId}")
    public String deleteDeviceVulnerability(Principal principal, @PathVariable(name="deviceId") Long did, @PathVariable(name="vulnerabilityId") Long vid) throws IllegalAccessException {
        deviceService.removeCveFromDevice(principal, did, vid);

        return "redirect:/device/edit/" + did;
    }
}
