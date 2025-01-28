# pdf_processor.py
import pdfplumber

def extract_questions_answers(pdf_path):
    questions_answers = {}
    with pdfplumber.open(pdf_path) as pdf:
        for page in pdf.pages:
            text = page.extract_text()
            lines = text.split('\n')
            for line in lines:
                if '?' in line:  # Supposons que les questions contiennent un '?'
                    question, answer = line.split('?', 1)
                    questions_answers[question.strip()] = answer.strip()
    return questions_answers

def get_answer(question, pdf_path='questions.pdf'):
    questions_answers = extract_questions_answers(pdf_path)
    return questions_answers.get(question, "Question not found.")
