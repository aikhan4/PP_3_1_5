document.addEventListener('DOMContentLoaded', function() {
    fetchCurrentUserAndPopulateHeader();
    fetchUsers();
});

function fetchUsers() {
    fetch('http://localhost:8080/api/getUsers')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            populateTable(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function concatenateRoleNames(users) {
    let roleNames = [];
    users.forEach(role => {
        roleNames.push(role.rolename.substring(5));
    });
    return roleNames.join(' ');
}

function getUserRole(user) {
    // Проверяем наличие роли "ROLE_ADMIN" у пользователя
    const isAdmin = user.roles.some(role => role.rolename === "ROLE_ADMIN");

    // Возвращаем соответствующую строку в зависимости от роли пользователя
    return isAdmin ? "ADMIN" : "USER";
}

function deleteUser(userId) {
    fetch(`http://localhost:8080/api/delete/${userId}`, {
        method: 'DELETE'
    })
        .then(response => {
            fetchUsers();
            return response.json();
        })
        .then(data => {
            // Если удаление прошло успешно, обновляем таблицу
            fetchUsers();
        })
}

function addEventListeners() {

}

function fetchCurrentUser() {
    fetch('http://localhost:8080/api/getCurrentUser')
        .then(response => {
            return response.json();
        })
}

function fetchCurrentUserAndPopulateHeader() {
    fetch('http://localhost:8080/api/getCurrentUser')
        .then(response => {
            return response.json();
        })
        .then(user => {
            populateHeader(user);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function populateHeader(user) {
    // Получаем элементы для заполнения
    let emailElement = document.querySelector('.userInfo .item:first-child p');
    let rolesElement = document.querySelector('.userInfo .item:nth-child(2) p');
    let roleNamesElement = document.querySelector('.userInfo .item:nth-child(3) p');

    // Заполняем элементы данными пользователя
    emailElement.textContent = user.email;
    roleNamesElement.textContent = concatenateRoleNames(user.roles);
}

// Вызываем функцию при загрузке страницы
fetchCurrentUserAndPopulateHeader();


function populateTable(data) {
    let tableBody = document.querySelector('.tableBody table tbody');

    tableBody.querySelectorAll('tr:not(:first-child)').forEach(row => row.remove());

    data.forEach(user => {
        let row = document.createElement('tr');
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${concatenateRoleNames(user.roles)}</td>
            <td class="tableButtonTd">
                <button class="btn btn-info btn-info-open" data-bs-toggle="modal"
                        data-bs-target="#exampleModal-1">
                    Edit
                </button>
                <div class="modal fade" id="exampleModal-1" tabindex="-1"
                     aria-labelledby="exampleModalLabel1" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Edit user</h5>
                                <button class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div class="tableBody">
                                    <form>
                                        <div class="items">
                                            <div class="item d-flex flex-column">
                                                <label class="form-label" for="id1">ID</label>
                                                <input class="form-control" type="text" name="id"
                                                       id="id1" readonly/>
                                            </div>

                                            <div class="item d-flex flex-column">
                                                <label class="form-label" for="firstName1">First Name</label>
                                                <input class="form-control" type="text" name="firstName"
                                                       id="firstName1"/>
                                            </div>

                                            <div class="item d-flex flex-column">
                                                <label class="form-label" for="lastName1">Last Name</label>
                                                <input class="form-control" type="text" name="lastName"
                                                       id="lastName1"/>
                                            </div>

                                            <div class="item d-flex flex-column">
                                                <label class="form-label" for="age1">Age</label>
                                                <input class="form-control" type="number" name="age"
                                                       id="age1"/>
                                            </div>

                                            <div class="item d-flex flex-column">
                                                <label class="form-label" for="email1">Email</label>
                                                <input class="form-control" type="text" name="email"
                                                       id="email1"/>
                                            </div>

                                            <div class="item d-flex flex-column">
                                                <label class="form-label" for="password1">Password</label>
                                                <input class="form-control" type="text" name="password"
                                                       id="password1"/>
                                            </div>

                                            <div class="item d-flex flex-column">
                                                <label class="form-label" for="role1">Role</label>
                                                <select class="form-control" name="role" id="role1" size="2">
                                                    <option value="ADMIN">ADMIN</option>
                                                    <option value="USER">USER</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">Close
                                            </button>
                                            <button type="submit" class="btn btn-info btn-info-send" data-bs-dismiss="modal">Edit</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </td>
            <td class="tableButtonTd">
                <button class="btn btn-danger btn-info-delete" data-user-id="${user.id}">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });

    // Добавляем обработчики событий на кнопки "Edit"
    let editButtons = document.querySelectorAll('.btn-info-open');
    editButtons.forEach((button, index) => {
        button.addEventListener('click', function() {
            let user = data[index];
            fillEditForm(user);
        });
    });

    // Добавляем обработчики событий на кнопки "Delete"
    let deleteButtons = document.querySelectorAll('.btn-info-delete');
    deleteButtons.forEach((button) => {
        button.addEventListener('click', function(event) {
            // Получаем ID пользователя для удаления из атрибута data-user-id кнопки
            let userId = event.target.dataset.userId;

            event.preventDefault();
            // Выполняем запрос на удаление пользователя

            deleteUser(userId);
            // Предотвращаем стандартное поведение кнопки

        });
    });
}

function fillEditForm(user) {
    // Получаем элементы формы для редактирования
    let idInput = document.getElementById('id1');
    let firstNameInput = document.getElementById('firstName1');
    let lastNameInput = document.getElementById('lastName1');
    let ageInput = document.getElementById('age1');
    let emailInput = document.getElementById('email1');
    let passwordInput = document.getElementById('password1');
    let roleInput = document.getElementById('role1');

    // Заполняем форму данными пользователя
    idInput.value = user.id;
    firstNameInput.value = user.firstName;
    lastNameInput.value = user.lastName;
    ageInput.value = user.age;
    emailInput.value = user.email;
    passwordInput.value = user.password;
    roleInput.value = getUserRole(user); // ошибка

    // Отправка данных при нажатии кнопки "Edit"
    let editForm = document.querySelector('#exampleModal-1 form');
    editForm.addEventListener('submit', function(event) {

        event.preventDefault(); // Предотвращаем отправку формы по умолчанию

        let modal = document.querySelector('#exampleModal-1');
        let modalInstance = bootstrap.Modal.getInstance(modal); // Получаем экземпляр модального окна
        modalInstance.hide(); // Закрываем модальное окно

        // Создаем объект с данными формы
        let formData = {
            id: idInput.value,
            firstName: firstNameInput.value,
            lastName: lastNameInput.value,
            age: parseInt(ageInput.value),
            email: emailInput.value,
            password: passwordInput.value,
            role: roleInput.value
        };

        // Отправляем данные на сервер с помощью AJAX-запроса
        fetch('http://localhost:8080/api/change', {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                fetchUsers();
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
}