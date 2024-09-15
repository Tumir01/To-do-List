function loadTasks() {
    fetch('http://localhost:8084/api/tasks')
        .then(response => response.json())
        .then(tasks => {
            const taskList = document.getElementById('taskList');
            taskList.innerHTML = '';
            tasks.forEach(task => {
                const li = document.createElement('li');
                li.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center');
                li.innerHTML = `
                    <span>
                        <strong>${task.title}</strong>: ${task.description} 
                    </span>
                    <button class="btn btn-danger btn-sm" onclick="deleteTask(${task.id})">Delete</button>
                `;
                taskList.appendChild(li);
            });
        })
        .catch(error => console.error('Error loading tasks', error));
}

function deleteTask(id) {
    fetch(`http://localhost:8084/api/tasks/${id}`, {
        method: 'DELETE'
    })
        .then(() => loadTasks())
        .catch(error => console.error('Error deleting task', error));
}

window.onload = function() {
    loadTasks();
};