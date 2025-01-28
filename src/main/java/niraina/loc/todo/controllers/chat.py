import sys
from fuzzywuzzy import fuzz
import fitz  # PyMuPDF
import re
import io
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')
sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')

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
    # Structurer les données de question-réponse
    qa_data = structurer_qa(texte_pdf)
    meilleure_score = 0
    meilleure_reponse = None

    # Comparer la question de l'utilisateur avec les questions du PDF
    for q in qa_data:
        score = fuzz.partial_ratio(question.lower(), q.lower())
        if score > meilleure_score:  # Garder la meilleure correspondance
            meilleure_score = score
            meilleure_reponse = qa_data[q]

    # Retourner la réponse si le score est suffisant
    if meilleure_score > 70:
        return meilleure_reponse
    else:
        return "Désolé, je n'ai pas de réponse appropriée."


# Extraire le texte du PDF
pdf_path = 'E:/chatExemple/Nouveau dossier/todo/todo/src/main/java/niraina/loc/todo/controllers/questions.pdf'  # Assurez-vous que le chemin du PDF est correct
texte_pdf = extraire_texte_pdf(pdf_path)

# Récupérer la question depuis les arguments de la ligne de commande
question = sys.argv[1] if len(sys.argv) > 1 else ""

# Appeler le chatbot avec la question et afficher la réponse
response = chatbot(texte_pdf, question)
print(response.encode('utf-8').decode('utf-8'))
