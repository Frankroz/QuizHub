document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("quizForm");

  if (!form) return;

  const questions = Array.from(
    document.querySelectorAll(".navigable-question"),
  );

  const questionButtons = Array.from(
    document.querySelectorAll(".question-nav-btn"),
  );

  const previousButton = document.getElementById("previousQuestion");
  const nextButton = document.getElementById("nextQuestion");
  const submitButton = document.getElementById("submitQuiz");

  const currentQuestionLabel = document.getElementById("currentQuestion");

  const totalQuestionsLabel = document.getElementById("totalQuestions");

  const answeredCountLabel = document.getElementById("answeredCount");

  const progressBar = document.getElementById("quizProgressBar");

  if (questions.length === 0) return;

  let currentIndex = 0;

  const totalQuestions = questions.length;

  totalQuestionsLabel.textContent = totalQuestions;

  // Check whether a question has an answer.
  function isAnswered(index) {
    const question = questions[index];

    return question.querySelector('input[type="radio"]:checked') !== null;
  }

  // Calculate the total number of answered questions.
  function getAnsweredCount() {
    return questions.filter((question) => {
      return question.querySelector('input[type="radio"]:checked') !== null;
    }).length;
  }

  // Update all question navigation buttons.
  function updateQuestionButtons() {
    questionButtons.forEach((button, index) => {
      const answered = isAnswered(index);

      button.classList.toggle("active", index === currentIndex);
      button.classList.toggle("answered", answered);

      button.classList.toggle(
        "unanswered",
        !answered && index !== currentIndex,
      );

      button.setAttribute(
        "aria-current",
        index === currentIndex ? "step" : "false",
      );
    });
  }

  // Update progress information.
  function updateProgress() {
    const answeredCount = getAnsweredCount();

    const percentage =
      totalQuestions > 0 ? (answeredCount / totalQuestions) * 100 : 0;

    answeredCountLabel.textContent = `${answeredCount} of ${totalQuestions} answered`;

    progressBar.style.width = `${percentage}%`;
  }

  // Display a specific question.
  function showQuestion(index) {
    if (index < 0 || index >= totalQuestions) return;

    currentIndex = index;

    questions.forEach((question, questionIndex) => {
      question.hidden = questionIndex !== currentIndex;
    });

    currentQuestionLabel.textContent = currentIndex + 1;

    previousButton.disabled = currentIndex === 0;

    const isLastQuestion = currentIndex === totalQuestions - 1;

    nextButton.hidden = isLastQuestion;
    submitButton.hidden = !isLastQuestion;

    updateQuestionButtons();
    updateProgress();

    // Move focus to the question for keyboard accessibility.
    const heading = questions[currentIndex].querySelector("h3");

    if (heading) {
      heading.setAttribute("tabindex", "-1");
      heading.focus({ preventScroll: true });
    }

    questions[currentIndex].scrollIntoView({
      behavior: "smooth",
      block: "start",
    });
  }

  // Previous question.
  previousButton.addEventListener("click", () => {
    showQuestion(currentIndex - 1);
  });

  // Next question.
  nextButton.addEventListener("click", () => {
    showQuestion(currentIndex + 1);
  });

  // Direct question navigation.
  questionButtons.forEach((button) => {
    button.addEventListener("click", () => {
      const index = Number(button.dataset.questionIndex);

      showQuestion(index);
    });
  });

  // Update progress whenever an answer changes.
  questions.forEach((question) => {
    question.addEventListener("change", () => {
      updateQuestionButtons();
      updateProgress();
    });
  });

  // Validate all questions before submitting.
  form.addEventListener("submit", (event) => {
    const firstUnansweredIndex = questions.findIndex(
      (_, index) => !isAnswered(index),
    );

    if (firstUnansweredIndex !== -1) {
      event.preventDefault();

      showQuestion(firstUnansweredIndex);

      alert(
        `Please answer question ${firstUnansweredIndex + 1} before submitting.`,
      );

      return;
    }

    const confirmed = window.confirm(
      "Are you sure you want to submit your answers? You cannot change them after submission.",
    );

    if (!confirmed) {
      event.preventDefault();
    }
  });

  // Initialize the quiz.
  showQuestion(0);
});
