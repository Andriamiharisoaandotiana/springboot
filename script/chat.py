import sys
from fuzzywuzzy import fuzz
import fitz  # PyMuPDF
import re

# Fonction pour extraire le texte du PDF
def extraire_texte_pdf(pdf_path):
    doc = fitz.open(pdf_path)
    texte = ""
    for page in doc:
        texte += page.get_text()
    return texte

# Fonction pour structurer les questions et réponses extraites du PDF
def structurer_qa(texte_pdf):
    pattern = re.compile(r"Q : (.*?)\nA : (.*?)(?=\nQ :|\Z)", re.DOTALL)
    qa_data = {}
    matches = pattern.findall(texte_pdf)
    for question, reponse in matches:
        qa_data[question.strip()] = reponse.strip()
    return qa_data

# Fonction pour répondre à une question similaire
def chatbot(texte_pdf, question):
    qa_data = structurer_qa(texte_pdf)
    for q in qa_data:
        if fuzz.partial_ratio(question.lower(), q.lower()) > 70:
            return qa_data[q]
    return "Désolé, je n'ai pas de réponse pour cela."

# Extraire le texte du PDF
pdf_path = 'questions.pdf'  # Assurez-vous que le chemin du PDF est correct
texte_pdf = extraire_texte_pdf(pdf_path)

# Récupérer la question depuis les arguments de la ligne de commande
question = sys.argv[1] if len(sys.argv) > 1 else ""

# Appeler le chatbot avec la question et afficher la réponse
response = chatbot(texte_pdf, question)
print(response)