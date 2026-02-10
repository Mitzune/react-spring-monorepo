# React Starter

This React project follows a feature-based architecture, designed for easy scalability. It allows new features to be added with minimal impact on existing code, improving maintainability and reducing the risk of introducing bugs.

## ✨ Adding a Feature

To add a new feature to the project:

1. **Create a new folder** inside the `features/` directory.
    - Each feature can be **domain-based** or **feature-based**, depending on your project organization.

2. **Add your main view** for the feature directly in the folder:
    - Example: `<feature>View.tsx`
    - Each feature folder can also contain its **own components**, **API calls**, **styles**, and other related files.

> This feature-based approach makes it easier to **add new functionality**, **maintain existing code**, and **scale the application** without affecting unrelated parts of the project.

💡 **Sample of this approach:**  
The **Home Page** and **Post Page** are implemented using this feature-based structure.

## 📦 Project Configuration Overview

This section describes the key parts of the `package.json` for this React project, including scripts, dependencies, devDependencies, and linting configuration.

---

### ⚡ Scripts

The `scripts` section defines common commands for development, building, linting, and previewing the project:

| Script    | Command                | Description                                                               |
| --------- | ---------------------- | ------------------------------------------------------------------------- |
| `dev`     | `vite`                 | 🚀 Starts the development server with hot module replacement (HMR).       |
| `build`   | `tsc -b && vite build` | 🏗️ Compiles TypeScript files and builds the production-ready Vite bundle. |
| `lint`    | `eslint .`             | 🔍 Runs ESLint on the entire project to check for code quality issues.    |
| `preview` | `vite preview`         | 👀 Serves the production build locally for preview purposes.              |

---

### 📚 Dependencies

These are runtime dependencies required for the project to function:

- **⚛️ React & React DOM**
    - `react` `^19.2.0`
    - `react-dom` `^19.2.0`
- **🌐 Routing**
    - `react-router` `^7.13.0`
- **🧠 State Management**
    - `zustand` `^5.0.11`
- **🎨 UI & Charts (Mantine & Tabler)**
    - `@mantine/core` `^8.3.14`
    - `@mantine/hooks` `^8.3.14`
    - `@mantine/form` `^8.3.14`
    - `@mantine/notifications` `^8.3.14`
    - `@mantine/charts` `^8.3.14`
    - `@tabler/icons-react` `^3.36.1`
- **📊 Charts Library**
    - `recharts` `^3.7.0`

> These dependencies provide the core functionality of the application including UI components, charts, state management, routing, and notifications.

---

### 🛠️ DevDependencies

These are tools used for development, linting, type-checking, and building:

- **📝 TypeScript & Types**
    - `typescript` `~5.9.3`
    - `@types/react` `^19.2.7`
    - `@types/react-dom` `^19.2.3`
    - `@types/node` `^24.10.1`
- **⚡ Vite & Plugins**
    - `vite` `^7.3.1`
    - `@vitejs/plugin-react` `^5.1.1`
- **🔧 Linting & Formatting**
    - `eslint` `^9.39.1`
    - `@eslint/js` `^9.39.1`
    - `@antfu/eslint-config` `^7.3.0`
    - `prettier` `^3.8.1`
    - `lint-staged` `^16.2.7`
- **🎨 CSS & PostCSS**
    - `postcss` `^8.5.6`
    - `postcss-preset-mantine` `^1.18.0`
    - `postcss-simple-vars` `^7.0.1`
- **🌐 Globals**
    - `globals` `^16.5.0`

> These tools help enforce code quality, enable TypeScript type checking, manage styles, and streamline development workflow.

---

### 🧹 Lint-Staged Configuration

The project uses `lint-staged` to run automatic formatting and linting before committing code:

```json
"lint-staged": {
  "**/*": "prettier --write --ignore-unknown --ignore-path",
  "*.{js,cjs,mjs,ts,jsx,tsx,vue}": "eslint --fix"
}
```
