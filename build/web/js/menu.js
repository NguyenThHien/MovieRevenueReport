function toggleSubmenu() {
    const submenu = document.getElementById("movie-submenu");
  
    const currentDisplay = window.getComputedStyle(submenu).display;
    
    submenu.style.display = (currentDisplay === "none") ? "flex" : "none";
}