// Функция для загрузки списка задач с сервера и отображения их на странице
function loadTasks() {
    fetch('http://localhost:8084/api/tasks')
        .then(response => response.json())
        .then(tasks => {
            const taskList = document.getElementById('taskList');
            taskList.innerHTML = ''; // Очищаем список перед добавлением обновленных задач
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

// Обработчик события для формы добавления новой задачи
const taskForm = document.getElementById('taskForm');
taskForm.addEventListener('submit', function (e) {
    e.preventDefault();

    const title = document.getElementById('taskName').value;  // Получаем значение заголовка задачи
    const description = document.getElementById('taskDescription').value;  // Получаем описание задачи

    fetch('http://localhost:8084/api/tasks', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title, description })  // Отправляем данные задачи на сервер
    })
        .then(response => response.json())
        .then(task => {
            taskForm.reset();  // Очищаем форму после успешного добавления задачи
            loadTasks();  // Перезагружаем список задач
        })
        .catch(error => console.error('Error adding task', error));
});

// Функция для удаления задачи по ее ID
function deleteTask(id) {
    fetch(`http://localhost:8084/api/tasks/${id}`, {
        method: 'DELETE'
    })
        .then(() => loadTasks())  // Перезагружаем список задач после удаления
        .catch(error => console.error('Error deleting task', error));
}

// Загрузка задач при загрузке страницы
window.onload = function() {
    loadTasks();  // Загрузка списка задач при первой загрузке страницы
};
