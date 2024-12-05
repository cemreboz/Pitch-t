# 📦 Pitch!t

Repository: https://github.com/cemreboz/Pitch-t


## 📖 Table of Contents
1. [🌟 Highlights](#-highlights)
2. [✍️ Authors](#%EF%B8%8F-authors)
3. [📖 Overview](#-overview)
4. [💡 Features](#-features)
5. [🚀 Usage](#-usage)
6. [⚙️ Installation](#%EF%B8%8F-installation)
7. [💭 Feedback and Contributing](#-feedback-and-contributing)
8. [📜 License](#-license)



## 🌟 Highlights

- **Team Story**: Generate and interact with AI-powered personas tailored to specific target audiences.
- **User Story #1**: Save and review session histories for iteration and improvement.
- **User Story #2**: Get contextual, real-time feedback on product ideas, pitches, and ads from experts.
- **User Story #3**: Compare personas to identify the best fit for your envisioned audience.
- **User Story #4**: Get detailed information on which audiences would be targeted by your pitch.
- **User Story #5**: Generate visuals for your products for AI analysis and feedback.



## ✍️ Authors

This project was created by Team Pitch!t as part of Group #159.

- Allan - https://github.com/just2ghosts
- Rainy - https://github.com/rainycore
- Viktor - https://github.com/vdomash
- Jad - https://github.com/lebgdecasa
- Cemre - https://github.com/cemreboz



## 📖 Overview

Pitch!t is an AI-powered platform designed to help entrepreneurs, marketers, and business professionals refine
their ideas and strategies by simulating conversations with AI personas. Whether you're testing a product idea,
pitching a business, or gauging the impact of a marketing campaign, Pitch!t helps you gain valuable insights from
personas crafted to reflect your target audience. We created this platform to empower aspiring entrepreneurs to better
understand their customers, validate their ideas, and refine their pitches — all with the help of cutting-edge AI.

PitchIt is a software designed to simplify pitch creation for entrepreneurs, startups, and marketers by automating the process of persona generation, target audience identification, and pitch analysis.
PitchIt was made to address the need for streamlined persona-based analysis when creating product pitches. It helps marketers quickly build target personas, compare them, and understand the best way to target potential customers.
This tool is ideal for entrepreneurs, startup founders, or marketing professionals looking to make informed decisions about product positioning, branding, and customer engagement.



## 💡 Features

- **AI Persona Generation**: Create detailed personas that represent your target audience based on specific criteria.
- **Session History**: Save and review past interactions to track progress and iterate on your strategies.
- **AI Expert Feedback**: Receive immediate, expert-level feedback on your pitches, product ideas, and advertising campaigns.
- **Persona Comparison**: Analyze and compare different personas to determine the most effective audience for your pitch.
- **Audience Insights**: Gain comprehensive information on the demographics and preferences of your targeted audiences.
- **Visual Generation**: Produce visuals for your products and receive AI-driven analysis and feedback to enhance your presentations.



## 🚀 Usage

### Steps to Use

1. **Log In/ Sign Up**: Access your Pitch!t account.
2. **Create a Pitch**: Input your product or business idea and description.
3. **Review Target Audiences**: Receive a detailed information about the target audiences.
4. **Generate Personas**: Create AI personas based on these demographics.
5. **Receive Feedback**: Chat with personas for real-time feedback.
6. **Ask predefined Expert AI**: Chat with a famous AI personality to received business feedback, the Shark Tank-style.
7. **Generate Visuals**: Use AI tools to create product visuals for specific personas.
8. **Refine and Iterate**: Create another pitch or iterate on the existing pitch by getting additional insights. 

### Example Workflow

1. Log in to your Pitch!t account.
2. Click "Create New Pitch" and input your product name and description.
3. Click "View Personas" to generate personas representing your target audience.
4. Select a persona you want to chat with, and press "Chat" to engage in an AI-driven conversations to test your pitch.
5. Press "Back" to go back to selection personas screen, select two personas and press "compare personas" to see detailed opinion comparison.
6. From the persona selection screen press, select a persona and press "Generate Vision" to see a tailored visual ad created for that persona to integrate into your final strategy.
7. Go back to the Pitch Selection any moment by pressing the "Hamburger Menu" button at the top left and then pressing "Dashboard"
8. From the "Hamburger Menu" or the "Dashboard" you can access "Experts" panel to ask AI-Expert personalities questions about your pitch. You can chat with Expert separately by selecting their approate chat.

## ⚙️ Installation

**Technical Requirements**
-  IntelliJ IDEA: Version 2020.1 or higher is recommended.
-  Download IntelliJ IDEA: https://www.jetbrains.com/idea/download
-  Java Development Kit (JDK): Version 11 or higher.
-  Download the JDK: https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
-  Maven: Required for dependency management.
-  Included with IntelliJ IDEA but can also be downloaded separately: https://maven.apache.org/download.cgi

**Supported Operating Systems**
-  Windows: Windows 10 or higher
-  macOS: macOS Mojave (10.14) or higher
-  Linux: Ubuntu 20.04 or higher, or any system with JDK 11+ installed

Follow these steps to install and set up **Pitch!t**:

1. Ensure you have the latest version of Intellij installed.

2. Clone the repository:

   ```
   git clone https://github.com/PitchIt/PitchIt.git
   
4. Launch IntelliJ IDEA on your system.
   - Click on File > Open.
   - Navigate to the directory where the repository was cloned and select the Pitch-t folder.
   - IntelliJ will automatically load the project. If prompted to import the project settings, choose Maven or Gradle (based on your setup).

4.5. Set API Key. (Optional)
   - Set environment variable "OPENAI_API_KEY" with your OpenAI key. https://openai.com/index/openai-api/
   - Locate all instances of PitchitManager.getApiKey(). (Double-shift to search project)
   - Repalce with System.getEnv("OPENAI_API_KEY"). 
  
5. Build the Project.
   - Ensure all dependencies are resolved:
   - IntelliJ will prompt you to download necessary dependencies automatically.
   - If not, open the Maven tool window (on the right side of IntelliJ) and click on Reload All Projects.
   - Wait for the build process to complete. If you see any errors, double-check your internet connection or re-import the project.

6. Run the Application
   - Locate the Main class in the app package.
   - Right-click the file and select Run 'Main'.
   - The application will start, and you can access its functionalities via the terminal or the GUI (if implemented).

## 💭 Feedback and Contributing

We value your feedback and encourage you to share your thoughts to help improve **Pitch!t**. Here's how you can provide feedback:

1. **Via GitHub Issues**:  
   - Navigate to the [GitHub Issues Page](https://github.com/cemreboz/Pitch-t/issues).
   - Click on **New Issue**.
   - Clearly describe the feedback, suggestion, or bug in the issue form.
   - If reporting a bug, include:
     - Steps to reproduce the issue
     - Expected vs. actual results
     - Screenshots or error logs (if applicable)

2. **Via Discussion Board**:  
   - Visit our [GitHub Discussions Page](https://github.com/cemreboz/Pitch-t/discussions) for general feedback or feature suggestions.
   - Start a new discussion or participate in an existing thread.

3. **Feedback Rules**:  
   - Provide specific, constructive feedback.
   - Avoid vague or overly general comments (e.g., "It doesn't work"). Instead, specify what aspect isn't working and why.
   - Be respectful in your communication.
  
4. **What to Expect After Submitting Feedback**:
   - Your feedback will be reviewed by the development team within **3-5 business days**.
   - Valid issues and suggestions will be added to the project roadmap or backlog.
   - You may be contacted for additional details or clarifications.
  
### Contributing to Pitch!t

We welcome contributions to **Pitch!t** from developers, designers, and anyone interested in improving the project.

#### How to Contribute
1. **Fork the Repository**:  
   - Navigate to the [Pitch!t Repository](https://github.com/cemreboz/Pitch-t) on GitHub.
   - Click the **Fork** button at the top-right corner.

2. **Clone Your Fork**:  
   ```bash
   git clone https://github.com/your-username/Pitch-t.git
   cd Pitch-t

3. Create a Feature Branch:
   ```bash
   git checkout -b feature/YourFeatureName

4. Make Your Changes:
   - Add your feature or fix a bug.
   - Ensure your code follows the project's coding standards and structure.
   - Include comments and documentation where necessary.
     
5. Test Your Changes:
   - Run all tests to ensure your changes do not introduce any bugs.
   - If necessary, add new tests to validate your work.

6. Commit and Push Your Changes:
   ```bash
   git add .
   git commit -m "Add Your Feature Description"
   git push origin feature/YourFeatureName

7. Submit a Pull Request (PR):

   - Navigate to your forked repository on GitHub.
   - Click the New Pull Request button.
   - Provide a detailed description of your changes in the PR form.
  
**Guidelines for Contributions**
   - Respect the Project's Vision: Ensure your changes align with the goals of the project.
   - Focus on Quality: Follow the coding standards and include tests for your code.
   - Provide Context: When submitting a PR, include:
   - The problem your contribution solves.
   - The solution you've implemented.
   - Any trade-offs or limitations.

**Reviewing and Merging Contributions**
   - Pull Request Review:
   - Your PR will be reviewed by a project maintainer within 5 business days.
   - Feedback may be provided to improve your contribution.

**Approval:**
   - If approved, your changes will be merged into the main branch.
   - Significant contributions may be highlighted in the project's release notes or README.

**Contributions That Are Not Accepted**
   - Submissions that introduce unnecessary complexity or deviate from the project goals.
   -Changes that are poorly documented or lack test coverage.
   - Contributions that violate the project's structure or do not adhere to Clean Architecture.
     
## 📜 License

This project is licensed under a **Public Domain Dedication** license. The **CC0 1.0 Universal** license explicitly waives all copyright and related rights, allowing the work to be freely used, shared, modified, and distributed by anyone, even for commercial purposes, without requiring permission or attribution.
