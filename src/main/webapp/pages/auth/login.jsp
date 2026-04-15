<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In — Mero Mart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
</head>
<body>
    <div class="split-layout">
        <!-- Left Side Image -->
        <div class="image-panel">
            <div class="tagline">Grown in the Himalayas</div>
            <h1>Harvesting the essence of freshness.</h1>
            <p class="sub-tagline">Direct from the local farmers of the valley to your kitchen.</p>
        </div>

        <!-- Right Side Form -->
        <div class="form-panel">
            <div class="form-wrapper">
                <img src="${pageContext.request.contextPath}/assets/images/logo/logo5.png"
                     alt="Mero Mart Logo" class="form-logo">
                <h2>Welcome back</h2>
                <p class="subtitle">Sign in to continue your journey.</p>

                <c:if test="${not empty requestScope.errorMessage}">
                    <div class="alert alert-error">
                        <c:out value="${requestScope.errorMessage}" />
                    </div>
                </c:if>

                <c:if test="${not empty requestScope.successMessage}">
                    <div class="alert alert-success">
                        <c:out value="${requestScope.successMessage}" />
                    </div>
                </c:if>

                <form action="${pageContext.request.contextPath}/login" method="POST">
                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <input type="email" id="email" name="email"
                               value="<c:out value="${savedEmail}" />" required>
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required>
                        <span class="password-toggle" onclick="togglePassword('password', this)">
                            <svg width="20" height="20" viewBox="0 0 24 24" fill="none"
                                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                                <circle cx="12" cy="12" r="3"></circle>
                            </svg>
                        </span>
                    </div>

                    <div class="form-row">
                        <label class="checkbox-label">
                            <input type="checkbox" name="rememberMe"
                                   ${not empty savedEmail ? 'checked' : ''}>
                            Remember Me
                        </label>
                        <a href="#" class="forgot-link forgot-pw">Forgot Password?</a>
                    </div>

                    <button type="submit" class="btn primary-btn">Sign In &rarr;</button>

                    <p class="switch-form">
                        Don't have an account?
                        <a href="${pageContext.request.contextPath}/register">Sign up for harvest</a>
                    </p>
                </form>
            </div>

            <div class="auth-footer">
                <span class="auth-footer-brand">MERO MART</span>
                <div class="auth-footer-links">
                    <a href="#">Privacy Policy</a>
                    <a href="#">Terms of Harvest</a>
                </div>
            </div>
        </div>
    </div>

    <script>
        function togglePassword(inputId, iconElement) {
            var input = document.getElementById(inputId);
            var eyeOpen  = '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle></svg>';
            var eyeClosed = '<svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"></path><line x1="1" y1="1" x2="23" y2="23"></line></svg>';
            if (input.type === "password") {
                input.type = "text";
                iconElement.innerHTML = eyeClosed;
            } else {
                input.type = "password";
                iconElement.innerHTML = eyeOpen;
            }
        }
    </script>
</body>
</html>