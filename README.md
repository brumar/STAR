# Introduction  
This repository presents a software built in order to use cognitive determinist models in a context of learning environements. The software is named STAR for Simple Toolbox to Analyse Reasoning. This repository may be hard to understand without related publications. The part of my PhD thesis related to this tool have been written but only in french for the moment. 

# A MDL-Based measure
The first specificity of this software is that it uses a new validity measure especially built for deterministic measure. This measure follows the MDL principle stating that a model is as good as its capacity to compress data. This principle is generally used in the context of probabilistic models and ask models to be "entropificated" via a loss function. Here an alternative trying to keep the deterministic aspect of the model is built.

# Writing and testing a model
STAR is built as a GUI to write and test a simple rule/constraint model. The tool is a bit minimalist and some improvements may be desirable to turn it into a more flexible software.

# How to use it
This project could be use as a Java project in your favorite IDE with a JRE superior or equal to the 1.7 version. Window.java contains the main you need.
I put a release with an exe and an executable jar file. Due to the presence of some (unused and soon to be deleted) jnlp file in the project, be aware that your anti-virus may scream a lot. If you need data and toy model to test the software, you can use files that are in the expertimentation subfolder. There are zip archive, don't hesitate to unzip them to check what they are made of.

# Statistical Analysis
MC_R_analysis contains a knitr notebook related to a statistical analysis based on regular STAR outputs. The goal was to test a simple model on data pertaining to word problem solving. To visualize the HTML file, please use the tool htmlpreview.github.io. 

# Last thing
Opening a research project is not an easy task. Please contact me if you are interested but have some difficulties to understand what's going on in this project or for any other reason related to the repository. If some people are interested I could make more substancial efforts to get this repository more "cognitively speaking" open.
