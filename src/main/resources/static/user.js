document.addEventListener('DOMContentLoaded', function() {
    fetchCurrentUserAndPopulateHeader();
    fillSoloUserTable();
});

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
function fillSoloUserTable() {
    // Выполняем запрос на получение данных о текущем пользователе
    fetchCurrentUser()
        .then(user => {
            // Находим ячейки таблицы, которые нужно заполнить
            const idCell = document.querySelector('.tableWrapper .bodyTr td:nth-child(1) p');
            const firstNameCell = document.querySelector('.tableWrapper .bodyTr td:nth-child(2) p');
            const lastNameCell = document.querySelector('.tableWrapper .bodyTr td:nth-child(3) p');
            const ageCell = document.querySelector('.tableWrapper .bodyTr td:nth-child(4) p');
            const emailCell = document.querySelector('.tableWrapper .bodyTr td:nth-child(5) p');
            const roleCell = document.querySelector('.tableWrapper .bodyTr td:nth-child(6) p');

            // Заполняем ячейки таблицы данными о пользователе
            idCell.textContent = user.id;
            firstNameCell.textContent = user.firstName;
            lastNameCell.textContent = user.lastName;
            ageCell.textContent = user.age;
            emailCell.textContent = user.email;
            roleCell.textContent = user.roles.map(role => role.rolename.substring(5)).join(' ');
        })
}
function fetchCurrentUser() {
    return fetch('http://localhost:8080/api/getCurrentUser')
        .then(response => {
            return response.json();
        })
        .catch(error => {
            console.error('Error fetching current user:', error);
        });
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