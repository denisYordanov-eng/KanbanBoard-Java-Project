package teistermask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import teistermask.bindingModel.TaskBindingModel;
import teistermask.entity.Task;
import teistermask.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TaskController {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        // TODO: Implement me...
        List<Task> tasks = taskRepository.findAll();

        List<Task> openTasks = tasks.stream()
                .filter(task -> task.getStatus()
                        .equals("Open"))
                .collect(Collectors.toList());
        List<Task> inProgress = tasks.stream()
                .filter(task -> task.getStatus()
                        .equals("In Progress"))
                .collect(Collectors.toList());
        List<Task> finished = tasks.stream()
                .filter(task -> task.getStatus()
                        .equals("Finished"))
                .collect(Collectors.toList());

        model.addAttribute("openTasks", openTasks);
        model.addAttribute("inProgressTasks", inProgress);
        model.addAttribute("finishedTasks", finished);

        model.addAttribute("view", "task/index");
        return "base-layout";
    }

    @GetMapping("/create")
    public String create(Model model) {
        // TODO: Implement me...
        model.addAttribute("view", "task/create");
        return "base-layout";
    }

    @PostMapping("/create")
    public String createProcess(Model model, TaskBindingModel taskBindingModel) {
        // TODO: Implement me...
        if (taskBindingModel == null) {
            return "redirect:/";
        }
        if (taskBindingModel.getTitle().equals("") || taskBindingModel.getStatus().equals("")) {
            return "redirect:/";
        }
        Task task = new Task();
        task.setTitle(taskBindingModel.getTitle());
        task.setStatus(taskBindingModel.getStatus());

        model.addAttribute("task", task);
        model.addAttribute("view", "task/create");

        taskRepository.saveAndFlush(task);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        // TODO: Implement me...
        Task task = taskRepository.findOne(id);
        if (task != null) {
            model.addAttribute("task", task);
            model.addAttribute("view", "task/edit");
            return "base-layout";
        }
        return "redirect:/";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable int id, TaskBindingModel taskBindingModel, Model model) {
        // TODO: Implement me...
        if (taskBindingModel == null) {
            return "redirect:/";
        }
        if (taskBindingModel.getTitle().equals("") || taskBindingModel.getStatus().equals("")) {
            return "redirect:/";

        }
        Task task = new Task();
        task.setId(id);
        task.setTitle(taskBindingModel.getTitle());
        task.setStatus(taskBindingModel.getStatus());

        model.addAttribute("task", task);
        model.addAttribute("view", "task/edit");
        taskRepository.saveAndFlush(task);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id,TaskBindingModel taskBindingModel, Model model) {
        Task task = taskRepository.findOne(id);
        if (task != null) {

            model.addAttribute("view", "task/delete");
            model.addAttribute("task", task);
            return "base-layout";
        }

        return "redirect:/";
    }
    @PostMapping("/delete/{id}")
    public String deleteProcess(Model Model, @PathVariable int id) {
        Task task = taskRepository.findOne(id);

        if (task == null) {
            return "redirect:/";
        }
       taskRepository.delete(task);
        taskRepository.flush();
        return "redirect:/";
    }
}
