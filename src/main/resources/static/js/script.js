let currentTheme = getTheme();
let changeThemeButton;

// initially execute -->
document.addEventListener('DOMContentLoaded', () => {
  changeTheme(currentTheme);
});

function changeTheme() {
  // set theme to web page
  document.querySelector('html').classList.add(currentTheme);

  // set the listener to change theme button
  changeThemeButton = document.getElementById('theme-change-btn'); // Assign the variable here

  // change the text of theme toggle button
  changeThemeButton.querySelector('span').textContent =
    currentTheme === 'light' ? 'Dark' : 'Light';

  changeThemeButton.addEventListener('click', (event) => {
    const oldTheme = currentTheme;
    if (currentTheme === 'dark') {
      currentTheme = 'light';
    } else {
      currentTheme = 'dark';
    }
    changePageTheme(currentTheme, oldTheme); // Call changePageTheme here to update the theme
  });
}

// set theme to local storage
function setTheme(theme) {
  localStorage.setItem('theme', theme);
}

// get theme from local storage
function getTheme() {
  let theme = localStorage.getItem('theme');
  return theme ? theme : 'light';
}

// change current page theme
function changePageTheme(theme, oldTheme) {
  // update theme to local storage
  setTheme(theme);

  // remove the current theme from website
  document.querySelector('html').classList.remove(oldTheme);

  // set the current theme to website
  document.querySelector('html').classList.add(theme);

  // change the text of theme toggle button
  changeThemeButton.querySelector('span').textContent =
    theme === 'light' ? 'Dark' : 'Light';
}
