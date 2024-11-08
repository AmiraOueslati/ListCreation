import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.example.task.model.Task


class TaskViewModel : ViewModel() {
    // État mutable pour la liste des tâches
    private val _tasks = mutableStateListOf<Task>().apply { addAll(List(100) { Task(it, "Task #$it") }) }
    val tasks: List<Task> = _tasks

    // Ajouter une nouvelle tâche
    fun addTask() {
        val newTask = Task(_tasks.size, "Task #${_tasks.size}")
        _tasks.add(newTask)
    }

    // Supprimer une tâche
    fun removeTask(task: Task) {
        _tasks.remove(task)
    }

    // Mettre à jour le statut d'une tâche
    fun updateTaskStatus(task: Task, isCompleted: Boolean) {
        task.isCompleted = isCompleted
    }
}
